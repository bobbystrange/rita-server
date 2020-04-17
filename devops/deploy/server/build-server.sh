#!/usr/bin/env bash

function info() {
    echo -e "\033[32m$@\033[0m"
}
function warn() {
    echo -e "\033[33m$@\033[0m"
}
function error() {
    echo -e "\033[31m$@\033[0m"
}

BIN=`which build-docker`
if [[ -z $BIN ]]; then
    error "Please define the cmd build-docker in your PATH"
    exit 1
fi

cd `dirname $0`
WORK_DIR=`pwd`
PROJECT_ROOT_DIR=../../..
cd $PROJECT_ROOT_DIR

RITA_VERSION=`./gradlew :rita-jpa:printVersion -q`
if [[ -z $RITA_VERSION ]]; then
    error "Please define printVersion task in build.gradle"
    exit 1
fi

./gradlew clean && ./gradlew -x test :rita-jpa:bootJar

cp ./rita-jpa/build/libs/rita-jpa-$RITA_VERSION.jar $WORK_DIR/rita-server-jpa.jar
cd $WORK_DIR

# docker login --username=tukeof registry.cn-hangzhou.aliyuncs.com
REGISTRY_PREFIX="registry.cn-hangzhou.aliyuncs.com/tuke"
IMAGE_NAME="$REGISTRY_PREFIX/rita-server-jpa:$RITA_VERSION"
$BIN $IMAGE_NAME .
docker push $IMAGE_NAME

rm rita-server-jpa.jar
