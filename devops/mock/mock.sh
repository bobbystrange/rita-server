#!/usr/bin/env bash

# mock server

./gradlew -x test :rita-api:bootJar && \
java -jar rita-api/build/libs/rita-api-0.1.0.jar
