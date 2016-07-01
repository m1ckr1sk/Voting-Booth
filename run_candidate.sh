#!/bin/bash
cd ./candidate
mvn spring-boot:run  2> errorOutput_candidate.log > output_candidate.log &