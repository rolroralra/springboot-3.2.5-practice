# TODO
- Github 의 Readme 에는 아래 내용이 포함되어야 합니다.
  - [x] 구현범위에대한설명
  - [x] 코드 빌드, 테스트, 실행 방법
  - [x] 기타추가정보
  - 구현 서비스
    - [x] 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
      - select category, brandName, itemPrice group by category
      - sum(itemPrice)
    - 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API
    ```json
    {
      "최저가" : {
      "브랜드" : "D", "카테고리" : [
      {"카테고리" : "상의", "가격" : "10,100"},
      {"카테고리" : "아우터", "가격" : "5,100"},
      {"카테고리" : "바지", "가격" : "3,000"},
      {"카테고리" : "스니커즈", "가격" : "9,500"},
      {"카테고리" : "가방", "가격" : "2,500"},
      {"카테고리" : "모자", "가격" : "1,500"},
      {"카테고리" : "양말", "가격" : "2,400"},
      {"카테고리" : "액세서리", "가격" : "2,000"}
      ],
      "총액" : "36,100" }
    }
    ```
  - [x] 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API
    - 요청값: 카테고리명
    - 응답값
    ```json
    {
      "카테고리": "상의",
      "최저가": [
        {
          "브랜드": "C",
          "가격": "10,000"
        }
      ],
      "최고가": [
        {
          "브랜드": "I",
          "가격": "11,400"
        }
      ]
    }
    ```
  - [x] 브랜드 및 상품을 추가 / 업데이트 / 삭제하는 API
    - `POST /api/v1/brands`
    - `PUT /api/v1/brand/{brand-id}`
    - `PATCH /api/v1/brands/{brand-id}`
    - `DELETE /api/v1/brands/{brand-id}`
    - `POST /api/v1/items`
    - `PUT /api/v1/items/{item-id}`
    - `PATCH /api/v1/items/{item-id}`
    - `DELETE /api/v1/items/{item-id}`
- 아래 기능은 포함하지 않아도 됩니다. 
  - ~~로그로깅~~
  - ~~모니터링~~
  - ~~CI/CD~~
  - ~~인증및권한~~
- [x] Unit test
  - `:repository:project-jpa-repository` 단위 테스트
- [x] Integration test
- [x] Frontend 페이지 구현

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
