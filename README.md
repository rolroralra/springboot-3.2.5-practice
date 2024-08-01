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

# Docker Run
```bash
docker run -p 8080:8080 --name project-api com.example.project-api:0.0.1-SNAPSHOT
```
