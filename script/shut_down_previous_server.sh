#!/bin/bash

echo "****************************************************************************************************"
echo ">"
echo "> shut_down_previous_server.sh"

PREVIOUS_PORT=$(<script/previous_port.txt)
PID=`lsof -t -i :$PREVIOUS_PORT`

for i in {1..3}; do
  if [ `lsof -t -i :$PREVIOUS_PORT` ]; then
    kill -2 $PID
    echo "> 기존 $PREVIOUS_PORT 포트 중지 시도"
    sleep 3
  fi
done

echo "> $(ps -ef | grep java)"

if [ `lsof -t -i :$PREVIOUS_PORT` ]; then
  echo "> 기존 $PREVIOUS_PORT 포트 강제 중지"
  kill -9 $PID
fi

echo ">"
echo "****************************************************************************************************"
