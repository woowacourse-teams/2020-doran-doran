#!/bin/bash

echo ">"
echo "> check_live_port.sh"

echo "> 살아있는 서버 체크"
PORT_STATUS_8080=$(curl --connect-timeout 3 -XGET https://woowacourse.com:8080/profile)
PORT_STATUS_8888=$(curl --connect-timeout 3 -XGET https://woowacourse.com:8888/profile)
echo ">"

if [ ! -z $PORT_STATUS_8080 ]; then
  echo "> 8080 작동 중"
  PREVIOUS_PORT=8080
elif [ ! -z $PORT_STATUS_8888 ]; then
  echo "> 8888 작동 중"
  PREVIOUS_PORT=8888
else
  PREVIOUS_PORT=8888
fi

echo $PREVIOUS_PORT > previous_port.txt