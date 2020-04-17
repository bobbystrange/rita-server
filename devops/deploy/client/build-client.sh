#!/usr/bin/env bash

BIN=`which build-docker`
if [[ -z $BIN ]]; then
    error "Please define the cmd build-docker in your PATH"
    exit 1
fi

cd `dirname $0`
WORK_DIR=`pwd`

cd ../../../../rita-client/
yarn run build:docker
cd $WORK_DIR
cp -r ../../../../rita-client/build .

RITA_VERSION="0.1.0"
REGISTRY_PREFIX="registry.cn-hangzhou.aliyuncs.com/tuke"
IMAGE_NAME="$REGISTRY_PREFIX/rita-client:$RITA_VERSION"
$BIN $IMAGE_NAME .
docker push $IMAGE_NAME

rm -rf build/
