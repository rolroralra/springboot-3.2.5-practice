provider "aws" {
  region = "ap-northeast-2"
}

# Lambda 역할 정의
resource "aws_iam_role" "lambda_role" {
  name = "lambda_role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Action = "sts:AssumeRole",
        Effect = "Allow",
        Principal = {
          Service = "lambda.amazonaws.com"
        }
      }
    ]
  })
}

# Lambda 역할에 대한 정책 정의
resource "aws_iam_role_policy" "lambda_policy" {
  name   = "lambda_policy"
  role   = aws_iam_role.lambda_role.id

  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Action = [
          "logs:CreateLogGroup",
          "logs:CreateLogStream",
          "logs:PutLogEvents"
        ],
        Effect   = "Allow",
        Resource = "*"
      },
      {
        Action = "lambda:InvokeFunction",
        Effect = "Allow",
        Resource = "*"
      }
    ]
  })
}

# Main Lambda Function 정의
resource "aws_lambda_function" "icare_main_function" {
  function_name = "icare-main-function"
  role          = aws_iam_role.lambda_role.arn
  handler       = "lambda_function.lambda_handler"
  runtime       = "python3.9"
  filename      = "icare_main_function.zip"

  source_code_hash = filebase64sha256("icare_main_function.zip")
}

# Lambda Authorizer 정의
resource "aws_lambda_function" "icare_authorizer_function" {
  function_name = "icare-authorizer-function"
  role          = aws_iam_role.lambda_role.arn
  handler       = "authorizer_function.lambda_handler"
  runtime       = "python3.9"
  filename      = "icare_authorizer_function.zip"

  source_code_hash = filebase64sha256("icare_authorizer_function.zip")
}

# CloudWatch Log Group for Main Lambda Function
resource "aws_cloudwatch_log_group" "icare_main_log_group" {
  name              = "/aws/lambda/icare-main-function"
  retention_in_days = 14
}

# CloudWatch Log Group for Authorizer Lambda Function
resource "aws_cloudwatch_log_group" "icare_authorizer_log_group" {
  name              = "/aws/lambda/icare-authorizer-function"
  retention_in_days = 14
}

# API Gateway REST API
resource "aws_api_gateway_rest_api" "icare_api" {
  name = "icare-api"
}

resource "aws_api_gateway_resource" "icare_resource" {
  rest_api_id = aws_api_gateway_rest_api.icare_api.id
  parent_id   = aws_api_gateway_rest_api.icare_api.root_resource_id
  path_part   = "icare"
}

resource "aws_api_gateway_method" "icare_method" {
  rest_api_id   = aws_api_gateway_rest_api.icare_api.id
  resource_id   = aws_api_gateway_resource.icare_resource.id
  http_method   = "POST"
  authorization = "CUSTOM"
  authorizer_id = aws_api_gateway_authorizer.icare_authorizer.id
}

# Lambda Authorizer 연결
resource "aws_api_gateway_authorizer" "icare_authorizer" {
  name            = "icare-authorizer"
  rest_api_id     = aws_api_gateway_rest_api.icare_api.id
  authorizer_uri  = aws_lambda_function.icare_authorizer_function.invoke_arn
  type            = "REQUEST"
  identity_source = "method.request.header.X-Device-ID"
}

# API Gateway와 Lambda Function 통합
resource "aws_api_gateway_integration" "icare_integration" {
  rest_api_id             = aws_api_gateway_rest_api.icare_api.id
  resource_id             = aws_api_gateway_resource.icare_resource.id
  http_method             = aws_api_gateway_method.icare_method.http_method
  integration_http_method = "POST"
  type                    = "AWS_PROXY"
  uri                     = aws_lambda_function.icare_main_function.invoke_arn
}

# API Gateway 배포
resource "aws_api_gateway_deployment" "icare_deployment" {
  depends_on = [aws_api_gateway_integration.icare_integration]
  rest_api_id = aws_api_gateway_rest_api.icare_api.id
  stage_name  = "prod"
}

# Lambda 함수의 권한 부여
resource "aws_lambda_permission" "apigw_lambda" {
  statement_id  = "AllowAPIGatewayInvoke"
  action        = "lambda:InvokeFunction"
  function_name = aws_lambda_function.icare_main_function.function_name
  principal     = "apigateway.amazonaws.com"
  source_arn    = "${aws_api_gateway_rest_api.icare_api.execution_arn}/*/*"
}
