#!/bin/bash

echo "****************************************************************************************************"
echo ">"
echo "> build_front.sh"

cd ./front
npm install

PREVIOUS_PORT=$(<../script/previous_port.txt)
echo "> 이전 서버 $PREVIOUS_PORT"

if [ $PREVIOUS_PORT == 8888 ]; then
	npm run build -- --mode development1 --http2
elif [ $PREVIOUS_PORT == 8080 ]; then
	npm run build -- --mode development2 --http2
else
  echo "> 오류 발생!"
fi

echo ">"
echo "****************************************************************************************************"
