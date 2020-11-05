#!/bin/bash

echo ">"
echo "> copy_properties.sh"

cp /home/ubuntu/mysecret/environment.txt ./script/environment.txt
ENVIRONMENT=$(<script/environment.txt)

cp /home/ubuntu/mysecret/$ENVIRONMENT-key/keystore.p12 ./src/main/resources/keystore.p12
cp /home/ubuntu/mysecret/application-dev.properties ./src/main/resources/application-$ENVIRONMENT.properties
cp /home/ubuntu/mysecret/application-security.properties ./src/main/resources/application-security.properties