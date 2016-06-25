#!/bin/bash
cd candidate
mvn clean package

cd ..
cd discovery
mvn clean package

cd ..
cd townhall
mvn clean package

cd ..
cd voters
mvn clean package

cd ..
cd booth
mvn clean package