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
echo "project java path: $PROTO_OUT_PATH"

PROTOC_PATH=${PROJECT_BASE_PATH}"/fabric-test/demo/src/github.com/xncc/"
echo "project java path: $PROTOC_PATH"

cd ${PROTOC_PATH}

## 删除已存在的protobuf go 文件
echo "---------------- delete exist proto go files ---------------- "
PROTO_GO_FILES="$(find protos -name '*.go')"
for file in ${PROTO_GO_FILES} ; do
    echo "----- delete file: $file"
    rm ${file}
done

echo " --------------- sync protos to java module -----------------"
cp -r protos/* ${PROJECT_JAVA_SRC}/src/main/proto/
cp -r protos/* ${PROJECT_BASE_PATH}/fabric-test/src/main/proto/

echo " --------------- compile protobuf to go -----------------"
## 编译protobuf go 文件
PROTO_FILES="$(find protos -name '*.proto' -exec dirname \{\} \; | sort |uniq)"

for dir in ${PROTO_FILES} ; do
    echo "-path $dir" ;
    protoc --proto_path ${PROTOC_PATH}/protos --go_out=${PROTO_OUT_PATH} ${PROTOC_PATH}/${dir}/*.proto
done


echo " --------------- compile protobuf to java -----------------"
#cd ${PROJECT_JAVA_SRC}
#mvn clean package
cd ${PROJECT_BASE_PATH}/fabric-test/
mvn clean package -Dmaven.test.skip=true

echo " --------------- build ccenv -----------------"
mkdir -p ${PROJECT_BASE_PATH}/fabric-test/demo/ccenv/github.com/xncc
cp -r ${PROTOC_PATH}/protos ${PROJECT_BASE_PATH}/fabric-test/demo/ccenv/github.com/xncc
cp -r ${PROTOC_PATH}/util ${PROJECT_BASE_PATH}/fabric-test/demo/ccenv/github.com/xncc

docker-compose -f ${PROJECT_BASE_PATH}/fabric-test/demo/ccenv-build.yaml build

echo "end"

