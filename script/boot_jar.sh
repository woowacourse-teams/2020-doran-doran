#!/bin/bash

echo ">"
echo "> boot_jar.sh"

ENVIRONMENT=$(<script/environment.txt)
PREVIOUS_PORT=$(<script/previous_port.txt)

if [ $ENVIRONMENT == prod ]; then
  DOMAIN=dorandoran.io
elif [ $ENVIRONMENT == dev ]; then
  DOMAIN=woowacourse.com
fi


if [ $PREVIOUS_PORT == 8080 ]; then
  IDLE_PROFILE=set2
  IDLE_PORT=8888
elif [ $PREVIOUS_PORT == 8888 ]; then
  IDLE_PROFILE=set1
  IDLE_PORT=8080
else
  IDLE_PROFILE=set1
  IDLE_PORT=8080
fi

echo ">"
echo "> IDLE_PROFILE = $IDLE_PROFILE"
echo "> IDLE_PORT = $IDLE_PORT"

echo ">"
echo "> application.jar 교체"
BASE_PATH=/home/ubuntu/deploy/jar
JAR_FILE=$(basename $(ls $BASE_PATH/*.jar))
IDLE_APPLICATION="$IDLE_PROFILE-$JAR_FILE"

echo ">"
echo "> $BASE_PATH/$IDLE_APPLICATION 생성"
cp $BASE_PATH/$JAR_FILE $BASE_PATH/$IDLE_APPLICATION

echo ">"
echo "> $IDLE_PORT 에서 작동 중인 애플리케이션 pid 확인"
IDLE_PID=$(pgrep -f $IDLE_APPLICATION)
if [ -z $IDLE_PID ]; then
  echo "> 현재 작동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -9 $IDLE_PID"
  kill -9 $IDLE_PID
  sleep 5
fi

echo ">"
echo "> $IDLE_APPLICATION 실행"
nohup java -jar -Duser.timezone=KST -Dspring.profiles.active=$ENVIRONMENT -Dspring.profiles.include=$IDLE_PROFILE $BASE_PATH/$IDLE_APPLICATION 1> /dev/null 2>&1 &

echo ">"
echo "> $IDLE_APPLICATION 10초 후 Health Check 시작"
echo "> curl -XGET https://$DOMAIN:$IDLE_PORT/actuator/health"
sleep 50

for retry_count in {1..10}
do
  response=$(curl -XGET https://$DOMAIN:$IDLE_PORT/actuator/health)
  up_count=$(echo $response | grep 'UP' | wc -l)

  echo ">"
  if [ $up_count -ge 1 ]
  then
    echo "> Health check 성공"
    break
  else
    echo "> Health check의 응답을 알 수 없거나 혹은 status가 UP이 아닙니다."
    echo "> Health check: ${response}"
  fi

  if [ $retry_count -eq 10 ]; then
    echo "> Health check 실패"
    echo "> Nginx에 연결하지 않고 배포를 종료합니다."
    exit 1
  fi

  echo "> Health check 연결 실패. 재시도..."
  sleep 10
done