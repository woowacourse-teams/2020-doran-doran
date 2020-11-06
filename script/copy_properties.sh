#!/bin/bash

echo "****************************************************************************************************"
echo ">"
echo "> copy_properties.sh"

cp /home/ubuntu/mysecret/environment.txt ./script/environment.txt
ENVIRONMENT=$(<script/environment.txt)

mkdir -p ./front/src/secure

cp /home/ubuntu/mysecret/appkey.js ./front/src/secure/appkey.js
cp /home/ubuntu/mysecret/tackingid.js ./front/src/secure/tackingid.js
cp /home/ubuntu/mysecret/$ENVIRONMENT-key/keystore.p12 ./src/main/resources/keystore.p12
cp /home/ubuntu/mysecret/application-$ENVIRONMENT.properties ./src/main/resources/application-$ENVIRONMENT.properties
cp /home/ubuntu/mysecret/application-security.properties ./src/main/resources/application-security.properties

echo ">"
echo "****************************************************************************************************"
