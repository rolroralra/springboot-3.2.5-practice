#!/bin/bash

AWS_ACCOUNT_ID="182043863214"
AWS_PROFILE="rolroralra"
AWS_REGION="ap-northeast-2"

aws ecr --profile ${AWS_PROFILE} get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com