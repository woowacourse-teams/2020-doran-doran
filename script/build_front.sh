#!/bin/bash
mkdir -p ./front/src/secure
KAKAO_APP_KEY=`cat $KAKAO_APP_KEY_FILE`
TRACKING_ID=`cat $TRACKING_ID_FILE`
echo $KAKAO_APP_KEY > ./front/src/secure/appkey.js
echo $TRACKING_ID > ./front/src/secure/trackingid.js

cd ./front
npm install

echo "> 현재 구동중인 Set 확인"
PROFILE_STATUS_8080=$(curl --connect-timeout 3 -XGET --fail-early https://woowacourse.com:8080/profile)
PROFILE_STATUS_8888=$(curl --connect-timeout 3 -XGET --fail-early https://woowacourse.com:8888/profile)
echo ">"
echo "> 8080 = $PROFILE_STATUS_8080"
echo "> 8888 = $PROFILE_STATUS_8888"


if [ $PROFILE_STATUS_8080 == 8080 ]; then
	npm run build -- --mode development1 --http2
elif [ $PROFILE_STATUS_8888 == 8888 ]; then
	npm run build -- --mode development2 --http2
else
	echo "> 일치하는 Profile이 없습니다. Profile: $PROFILE_STATUS_8080"
  echo "> 일치하는 Profile이 없습니다. Profile: $PROFILE_STATUS_8888"
fi