#!/bin/bash
cd ./discovery 
mvn spring-boot:run  2> errorOutput_disco.log > output_disco.log &