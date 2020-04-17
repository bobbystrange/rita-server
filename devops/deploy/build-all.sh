#!/usr/bin/env bash

cd `dirname $0`

sh ./client/build-client.sh
sh ./face/build-face.sh
sh ./server/build-server.sh
