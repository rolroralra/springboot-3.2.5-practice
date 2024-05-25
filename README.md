# TODO
- Github 의 Readme 에는 아래 내용이 포함되어야 합니다.
  - [ ] 구현범위에대한설명
  - [x] 코드 빌드, 테스트, 실행 방법
  - [ ] 기타추가정보
- 아래 기능은 포함하지 않아도 됩니다. 
  - ~~로그로깅~~
  - ~~모니터링~~
  - ~~CI/CD~~
  - ~~인증및권한~~
- [ ] Unit test
  - `:repository:project-jpa-repository` 단위 테스트
  - 
- [ ] Integration test
- [ ] Frontend 페이지 구현

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
