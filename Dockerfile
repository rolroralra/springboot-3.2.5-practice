FROM openjdk:21-slim AS builder
WORKDIR /usr/src/app

COPY ./gradle/ ./gradle/
COPY ./build.gradle.kts ./build.gradle.kts
COPY ./settings.gradle.kts ./settings.gradle.kts
COPY ./gradle.properties ./gradle.properties
COPY ./subprojects/ ./subprojects/
COPY ./gradlew ./gradlew
COPY ./gradlew.bat ./gradlew.bat

RUN ./gradlew --daemon :server:project-api:clean :server:project-api:build -x test

FROM openjdk:21-slim AS layer_extractor
WORKDIR /usr/src/app/build

COPY --from=builder usr/src/app/subprojects/server/project-api/build/libs/*.jar ./app.jar

RUN ["java", "-Djarmode=layertools", "-Dspring.profiles.active=prod", "-jar", "app.jar", "extract"]

FROM openjdk:21-slim AS runner
WORKDIR /usr/bin/app

COPY --from=layer_extractor /usr/src/app/build/dependencies/ ./
COPY --from=layer_extractor /usr/src/app/build/spring-boot-loader/ ./
COPY --from=layer_extractor /usr/src/app/build/snapshot-dependencies/ ./
COPY --from=layer_extractor /usr/src/app/build/application/ ./
COPY docker/run-java.sh /usr/bin/run-java.sh

RUN apt-get update \
  && apt-get install -y wget  \
  && apt-get clean

# RUN wget -O dd-java-agent.jar 'https://github.com/DataDog/dd-trace-java/releases/latest/download/dd-java-agent.jar'

ENV JAVA_MAIN_CLASS=org.springframework.boot.loader.launch.JarLauncher
ENV JAVA_APP_DIR=/usr/bin/app
ENV JAVA_LIB_DIR=/usr/bin/app
ENV JAVA_OPTIONS="-ea -XX:+UseG1GC -XX:MaxGCPauseMillis=100 -XX:+ExitOnOutOfMemoryError"

ENTRYPOINT [ "sh", "/usr/bin/run-java.sh" ]