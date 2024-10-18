FROM openjdk:21-slim-buster AS Builder
WORKDIR /usr/bin/app
COPY ../build/libs/*.jar app.jar

RUN ["java", "-Djarmode=layertools", "-Dspring.profiles.active=prod", "-jar", "app.jar", "extract"]

FROM openjdk:21-slim-buster AS runner
ARG WORK_DIR=/usr/bin/app
#ARG MYSQL_URL
#ARG MYSQL_USERNAME
#ARG MYSQL_PASSWORD
WORKDIR ${WORK_DIR}

#ENV MYSQL_URL=${MYSQL_URL}
#ENV MYSQL_USERNAME=${MYSQL_USERNAME}
#ENV MYSQL_PASSWORD=${MYSQL_PASSWORD}

COPY --from=builder ${WORK_DIR}/dependencies/ ./
COPY --from=builder ${WORK_DIR}/spring-boot-loader/ ./
COPY --from=builder ${WORK_DIR}/snapshot-dependencies/ ./
COPY --from=builder ${WORK_DIR}/application/ ./
COPY docker/run-java.sh /usr/bin/run-java.sh

RUN apt-get update \
  && apt-get install -y wget  \
  && apt-get clean

# RUN wget -O dd-java-agent.jar 'https://github.com/DataDog/dd-trace-java/releases/latest/download/dd-java-agent.jar'

ENV JAVA_MAIN_CLASS org.springframework.boot.loader.launch.JarLauncher
ENV JAVA_APP_DIR /usr/bin/app
ENV JAVA_LIB_DIR /usr/bin/app
ENV JAVA_OPTIONS "-ea -XX:+UseG1GC -XX:MaxGCPauseMillis=100 -XX:+ExitOnOutOfMemoryError"

ENTRYPOINT [ "sh", "/usr/bin/run-java.sh" ]