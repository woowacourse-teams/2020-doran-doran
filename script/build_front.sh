#!/bin/bash

echo ">"
echo "> build_front.sh"

mkdir -p ./front/src/secure
KAKAO_APP_KEY=`cat $KAKAO_APP_KEY_FILE`
TRACKING_ID=`cat $TRACKING_ID_FILE`
echo $KAKAO_APP_KEY > ./front/src/secure/appkey.js
echo $TRACKING_ID > ./front/src/secure/trackingid.js

cd ./front
npm install

PREVIOUS_PORT=$(<previous_port.txt)
echo "> 이전 서버 $(PREVIOUS_PORT)"

if [ $PREVIOUS_PORT == 8888 ]; then
	npm run build -- --mode development1 --http2
elif [ $PREVIOUS_PORT == 8080 ]; then
	npm run build -- --mode development2 --http2
else
  echo ">오류 발생!"
fi