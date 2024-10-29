#!/bin/bash
RELEASE_VERSION=${RELEASE_VERSION:LATEST};

cd ..

docker build -t com.example.project-api:${RELEASE_VERSION} .