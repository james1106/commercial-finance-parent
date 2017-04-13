#!/usr/bin/env bash

## 设置项目的基路径
PROJECT_BASE_PATH=$1
if [ -z "$1" ]; then
	PROJECT_BASE_PATH="/Users/liuzhudong/06git/xn/commercial-finance"
	echo "set project base path to default: "${PROJECT_BASE_PATH}
fi

echo "set project base path to: "${PROJECT_BASE_PATH}
echo

## 设置protoc的环境变量
export PROTOC_HOME=/Users/liuzhudong/protoc-3
export PATH=$PATH:${PROTOC_HOME}/bin

## 设置项目的相关路径
cd ${PROJECT_BASE_PATH}
echo "project base path: $PROJECT_BASE_PATH"

PROJECT_JAVA_SRC=${PROJECT_BASE_PATH}"/data-design"
echo "project java path: $PROJECT_JAVA_SRC"

PROTO_OUT_PATH=${PROJECT_BASE_PATH}"/fabric-test/demo/src"
echo "protoc out go path: $PROTO_OUT_PATH"

PROTO_OUT_JAVA_PATH=${PROJECT_BASE_PATH}"/server-protobuf-data/src/main/java/"
echo "protoc out java path: "${PROTO_OUT_JAVA_PATH}

PROTOC_PATH=${PROJECT_BASE_PATH}"/fabric-test/demo/src/github.com/xncc/"
echo "protos source path: $PROTOC_PATH"



cd ${PROTOC_PATH}

## 删除已存在的protobuf go 文件
echo "---------------- delete exist proto go files ---------------- "
PROTO_GO_FILES="$(find protos -name '*.go')"
for file in ${PROTO_GO_FILES} ; do
    echo "----- delete file: $file"
    rm ${file}
done

echo " --------------- sync protos to java module -----------------"
cp -r protos/* ${PROJECT_BASE_PATH}/server-protobuf-data/src/main/proto/

echo " --------------- compile protobuf to go -----------------"
## 编译protobuf go 文件
PROTO_FILES="$(find protos -name '*.proto' -exec dirname \{\} \; | sort |uniq)"

for dir in ${PROTO_FILES} ; do
    echo "-path $dir" ;
    protoc --proto_path ${PROTOC_PATH}/protos --go_out=${PROTO_OUT_PATH} ${PROTOC_PATH}/${dir}/*.proto
    protoc --proto_path ${PROTOC_PATH}/protos --java_out=${PROTO_OUT_JAVA_PATH} ${PROTOC_PATH}/${dir}/*.proto
done

## 编译java的代码
echo " --------------- compile protobuf to java -----------------"
cd ${PROJECT_BASE_PATH}/server-protobuf-data
mvn clean install -Dmaven.test.skip=true

## 重新编译ccenv的docker image
echo " --------------- build ccenv -----------------"
cd ${PROJECT_BASE_PATH}/fabric-test/demo/ccenv/github.com/

## 删除已存在的ccenv依赖编译环境
CCENV_GO_FILE_DIR="$(find xncc -name '*.go' -exec dirname \{\} \; | sort |uniq)"
for dir in ${CCENV_GO_FILE_DIR} ; do
    echo "----- delete ccenv gopath files: $dir/*"
    rm -r ${dir}
done

## 同步新的ccenv依赖编译环境文件
mkdir -p ${PROJECT_BASE_PATH}/fabric-test/demo/ccenv/github.com/xncc
cp -r ${PROTOC_PATH}/protos ${PROJECT_BASE_PATH}/fabric-test/demo/ccenv/github.com/xncc
cp -r ${PROTOC_PATH}/util ${PROJECT_BASE_PATH}/fabric-test/demo/ccenv/github.com/xncc

## 复制protobuf 代码
echo "------------- go get proto "
go get github.com/golang/protobuf/proto
rm -r ${PROJECT_BASE_PATH}/fabric-test/demo/ccenv/github.com/golang/protobuf/proto
mkdir -p ${PROJECT_BASE_PATH}/fabric-test/demo/ccenv/github.com/golang/protobuf/proto

echo "------------- copy golang/protobuf/proto "
cp -r ${GOPATH}/src/github.com/golang/protobuf/proto/ ${PROJECT_BASE_PATH}/fabric-test/demo/ccenv/github.com/golang/protobuf/proto

echo "------------- docker build "
docker-compose -f ${PROJECT_BASE_PATH}/fabric-test/demo/ccenv-build.yaml build

## 删除空的docker image
echo " --------------- remove docker image none -----------------"
DOCKER_IMAGES_NONE="$(docker images | grep \<none\> | awk '{print $3}')"
for image in ${DOCKER_IMAGES_NONE} ; do
    echo "remove docker image: "${image}
    docker rmi ${image}
done

echo "end"

