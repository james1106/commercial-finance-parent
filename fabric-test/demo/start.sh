#!/usr/bin/env bash

export ORG_HYPERLEDGER_FABRIC_SDKTEST_INTEGRATIONTESTS_TLS=false
export ORG_HYPERLEDGER_FABRIC_SDKTEST_INTEGRATIONTESTS_CA_TLS=false
export CORE_VM_DOCKER_HOSTCONFIG_NETWORKMODE=demo_default


echo ".............. stop current dockers ...................."
docker-compose down

DOCKER_CONTAINERS=`docker ps -aq`
if [ ${#DOCKER_CONTAINERS} -gt 0 ]
then
    echo ".............. rm all docker containers ....................."
    docker rm $(docker ps -aq)

fi

#
DOCKER_IMAGES=`docker images | grep dev-peer | awk '{print $3}'`
if [ ${#DOCKER_IMAGES} -gt 0 ]
then
    echo ".............. rm all peer cc docker images ....................."
    docker rmi $(docker images | grep dev-peer | awk '{print $3}')

fi

echo ".............. start all docker containers ....................."
docker-compose up -d

#echo "rm all dockers ....................."
#DOCKER_CONTAINERS = `$(docker ps -aq)`
#echo ${DOCKER_CONTAINERS[@]}
#docker rm $(docker ps -aq)
#
#echo "rm cc docker images .................."
#docker rmi $(docker images | grep dev-peer | awk '{print $3}')
#
#echo "start run docker command ....................."
#docker-compose up -d
#
#NAME[0]="Zara"
#NAME[1]="Qadir"
#NAME[2]="Mahnaz"
#NAME[3]="Ayan"
#NAME[4]="Daisy"
#echo "First Index: ${NAME[0]}"
#echo "Second Index: ${NAME[1]}"
#echo "length Index: ${#NAME[@]}"
#
##DOCKER_CONTAINERS=`docker images | grep dev-peer | awk '{print $3}'`
#DOCKER_CONTAINERS=`docker ps -aq`
#XXXXX=`docker ps -aq}`
#echo "length: ${#XXXXX[@]}"
#echo "all: ${XXXXX[@]}"
#echo "string length: ${#DOCKER_CONTAINERS}"
#
#if [ ${#DOCKER_CONTAINERS} == 0 ]
#then
#    echo "null"
#fi

echo "end"
