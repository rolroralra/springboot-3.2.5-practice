# 디렉토리 구조
```
terraform-project/
├── main.tf
├── lambda/
│   ├── icare_main_function/
│   │   ├── lambda_function.py
│   │   └── icare_main_function.zip  # 이 파일은 build/ 배치 프로세스에서 생성됨
│   └── icare_authorizer_function/
│       ├── authorizer_function.py
│       └── icare_authorizer_function.zip  # 이 파일은 build/ 배치 프로세스에서 생성됨
└── README.md  # 프로젝트 설명 및 배포 방법
```

# 디렉토리 및 파일 설명

1. **`main.tf`**:
    - Terraform의 메인 구성 파일로, AWS 리소스들을 정의하는 곳입니다. 이 파일에 API Gateway, Lambda 함수, IAM 역할 및 정책 등을 설정합니다.

2. **`lambda/`**:
    - Lambda 함수 코드를 저장하는 디렉토리입니다. 이 디렉토리 아래에 각각의 Lambda 함수 디렉토리가 있고, 각 디렉토리 안에 소스 코드 파일과 함께 압축 파일(`.zip`)이 위치합니다.

3. **`lambda/icare_main_function/`**:
    - `icare-main-function` Lambda 함수의 소스 코드가 위치한 디렉토리입니다.
    - `lambda_function.py`는 Lambda 함수의 소스 코드입니다.
    - `icare_main_function.zip`은 `lambda_function.py` 파일을 압축한 파일로, Terraform에서 이 파일을 사용해 Lambda 함수를 생성합니다.

4. **`lambda/icare_authorizer_function/`**:
    - `icare-authorizer-function` Lambda 함수의 소스 코드가 위치한 디렉토리입니다.
    - `authorizer_function.py`는 Lambda Authorizer의 소스 코드입니다.
    - `icare_authorizer_function.zip`은 `authorizer_function.py` 파일을 압축한 파일로, Terraform에서 이 파일을 사용해 Lambda Authorizer를 생성합니다.

5. **`README.md`**:
    - 프로젝트에 대한 설명 및 배포 방법을 설명하는 파일입니다. Terraform 명령어를 사용하는 방법과 프로젝트 설정에 대한 간단한 가이드를 포함할 수 있습니다.

# Terraform을 실행하기 전 준비 단계

1. **Python 파일 압축**:
   각 Lambda 함수 디렉토리(`icare_main_function`, `icare_authorizer_function`)에서 해당 Python 파일을 `.zip`으로 압축합니다.

   ```bash
   cd terraform-project/lambda/icare_main_function
   zip -r icare_main_function.zip icare_main_function.py
   ```

   ```bash
   cd terraform-project/lambda/icare_authorizer_function
   zip -r icare_authorizer_function.zip icare_authorizer_function.py
   ```

2. **Terraform 실행**:
   압축 파일들이 준비되면, 프로젝트 루트(`terraform-project/`)에서 Terraform을 실행합니다.

   ```bash
   terraform init
   terraform apply
   ```

이 구조는 확장 가능하고 유지보수가 용이하도록 설계되었습니다. 각 Lambda 함수는 별도의 디렉토리에서 관리되며, 이 디렉토리에는 해당 Lambda 함수에 관련된 모든 코드와 파일이 포함됩니다. Terraform 구성은 루트 디렉토리에 위치하여 프로젝트 전체를 관리합니다.