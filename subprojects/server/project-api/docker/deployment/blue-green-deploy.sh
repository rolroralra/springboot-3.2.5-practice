#!/bin/bash

# ANSI escape codes for color
GREEN='\033[0;32m'
BLUE='\033[0;34m'
RED='\033[0;31m'
RESET='\033[0m'


# 1. AWS ECR Login
DIR_NAME=$(dirname $0)
bash ${DIR_NAME}/aws_ecr_login.sh

# 2. Deploy
PROJECT_NAME="project-api"

# Check the currently deployed container based on Blue Deployment
BEFORE_DEPLOYED_CONTAINER=$(docker-compose -p ${PROJECT_NAME}-blue -f docker-compose.blue.yaml ps | grep Up | grep ${PROJECT_NAME}-blue)
echo
echo -e "${BLUE}Checking the currently deployed container...${RESET}"
echo

# Container Switching
if [ -z "$BEFORE_DEPLOYED_CONTAINER" ]; then
    docker-compose -p ${PROJECT_NAME}-blue -f docker-compose.blue.yaml up -d
    BEFORE_COMPOSE_COLOR="green"
    AFTER_COMPOSE_COLOR="blue"
    BEFORE_CONTAINER_COLOR=${GREEN}
    CONTAINER_COLOR=${BLUE}
else
    docker-compose -p ${PROJECT_NAME}-green -f docker-compose.green.yaml up -d
    BEFORE_COMPOSE_COLOR="blue"
    AFTER_COMPOSE_COLOR="green"
    BEFORE_CONTAINER_COLOR=${BLUE}
    CONTAINER_COLOR=${GREEN}
fi

echo
echo -e "Before Deployment Color : ${BEFORE_CONTAINER_COLOR}${BEFORE_COMPOSE_COLOR}${RESET}"
echo -e "After  Deployment Color : ${CONTAINER_COLOR}${AFTER_COMPOSE_COLOR}${RESET}"
echo

sleep 10

# Check whether the new container has been launched properly
NEW_DEPLOYED_CONTAINER=$(docker-compose -p ${PROJECT_NAME}-${AFTER_COMPOSE_COLOR} -f docker-compose.${AFTER_COMPOSE_COLOR}.yaml ps | grep Up | grep ${PROJECT_NAME}-${AFTER_COMPOSE_COLOR})
echo -e "${BLUE}Checking whether the new container has been launched properly.${RESET}"
echo -e "${NEW_DEPLOYED_CONTAINER}"
echo

LOOP_INDEX=0
SUCCESS_YN=N
CHECK_HEALTH_STATUS=$( docker inspect --format="{{ .State.Health.Status }}" ${PROJECT_NAME}-${AFTER_COMPOSE_COLOR} 2> /dev/null )
echo -e "${BLUE}Checking${RESET} ${CONTAINER_COLOR}${PROJECT_NAME}-${AFTER_COMPOSE_COLOR}${RESET} Health Check Status${RESET}"
echo

while [ $LOOP_INDEX -le 5 ]
do
  LOOP_INDEX=$(($LOOP_INDEX+1))
  echo -e "${BLUE}Health Check Try-Count:${RESET} ${LOOP_INDEX}"
  CHECK_HEALTH_STATUS=$( docker inspect --format="{{ .State.Health.Status }}" ${PROJECT_NAME}-${AFTER_COMPOSE_COLOR} 2> /dev/null )

  # status check
  if [ "${CHECK_HEALTH_STATUS}" == "healthy" ]; then
    echo -e "New container health check status : ${GREEN}${CHECK_HEALTH_STATUS}${RESET}"
    echo

    SUCCESS_YN=Y;
    break;
  fi

  if [ "${CHECK_HEALTH_STATUS}" != "healthy" ]; then
    echo -e "New container health check status : ${GREEN}${CHECK_HEALTH_STATUS}${RESET}"
    echo
    if [ $LOOP_INDEX -eq 5 ]; then
      echo -e "${RED}The new container is terminated, because its status is abnormal.${RESET}"
      echo
      docker-compose -p ${PROJECT_NAME}-${AFTER_COMPOSE_COLOR} -f docker-compose.${AFTER_COMPOSE_COLOR}.yaml down
      exit 1
    fi
  fi
  sleep 10 # 10초 대기
done

if [ "${SUCCESS_YN}" == "Y" ]; then
    # 이전 컨테이너 종료
    echo -e "${BLUE}Previous Container Shut-down Start : ${RESET}${BEFORE_CONTAINER_COLOR}$BEFORE_COMPOSE_COLOR${RESET} down START"
    echo
    docker-compose -p ${PROJECT_NAME}-${BEFORE_COMPOSE_COLOR} -f docker-compose.${BEFORE_COMPOSE_COLOR}.yaml down
    echo -e "${BLUE}Previous Container Shut-down Complete : ${RESET}${BEFORE_CONTAINER_COLOR}$BEFORE_COMPOSE_COLOR${RESET} down COMPLETE"
    echo
    echo -e "${BLUE}New Container Start-up Complete : ${RESET}${CONTAINER_COLOR}$AFTER_COMPOSE_COLOR${RESET} up COMPLETE"
    echo
    echo -e "${CONTAINER_COLOR}${PROJECT_NAME}-${AFTER_COMPOSE_COLOR}${RESET} Deployed COMPLETED"
    echo
fi
