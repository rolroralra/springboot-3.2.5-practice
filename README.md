# Development Environment
- Java 21
- Spring Boot 3.2.5
- Gradle 8.5
- Docker 26.0.0
- Docker-Compose 2.26.0

# Build
```bash
./gradlew clean build
```

# Test
```bash
./gradlew test
```

# Run
```bash
./gradlew :server:project-api:bootRun
```

# Docker Image Build
```bash
./gradlew :server:project-api:bootBuildImage
```

```bash
export RELEASE_VERSION=0.0.3;

cd subprojects/server/project-api/docker/deployment

bash 1_docker_image_build.sh

bash 2_docker_image_push.sh
```

# Docker Run Local
```bash
docker run -p 8080:8080 --name project-api com.example.project-api:${TAG}
```
