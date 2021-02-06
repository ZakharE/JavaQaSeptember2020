#! /bin/sh
docker-compose up -d
docker-compose ps
cd ../
pwd
mvn clean test