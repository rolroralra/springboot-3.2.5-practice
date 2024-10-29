#!/bin/bash

RELEASE_VERSION=${RELEASE_VERSION:LATEST};
AWS_ACCOUNT_ID="182043863214"
AWS_REGION="ap-northeast-2"
AWS_ECR_REPOSITORY="project-api"

bash ./aws_ecr_login.sh

docker tag com.example.project-api:${RELEASE_VERSION} ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${AWS_ECR_REPOSITORY}:${RELEASE_VERSION}

docker push ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${AWS_ECR_REPOSITORY}:${RELEASE_VERSION}