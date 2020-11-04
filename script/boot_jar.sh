#!/bin/bash

BASE_PATH=/home/ubuntu/dev
DEPLOY_PATH=$BASE_PATH/jar/
BUILD_PATH=$(ls $BASE_PATH/jar/*.jar)
JAR_NAME=$(basename $BUILD_PATH)

echo "> build 파일명: $JAR_NAME"
echo "> 현재 구동중인 Set 확인"
PROFILE_STATUS_8080=$(curl --connect-timeout 3 -XGET https://woowacourse.com:8080/profile)
PROFILE_STATUS_8888=$(curl --connect-timeout 3 -XGET https://woowacourse.com:8888/profile)
echo ">"
echo "> 8080 = $PROFILE_STATUS_8080"
echo "> 8888 = $PROFILE_STATUS_8888"

if [ $PROFILE_STATUS_8080 == 8080 ]; then
  IDLE_PROFILE=set2
  IDLE_PORT=8888
  PAST_PORT=8080
elif [ $PROFILE_STATUS_8888 == 8888 ]; then
  IDLE_PROFILE=set1
  IDLE_PORT=8080
  PAST_PORT=8888
else
  echo ">"
  echo "> 일치하는 Profile이 없습니다. Profile: $PROFILE_STATUS_8080"
  echo "> 일치하는 Profile이 없습니다. Profile: $PROFILE_STATUS_8888"
  echo "> set1을 할당합니다. IDLE_PROFILE: set1"
  IDLE_PROFILE=set1
  IDLE_PORT=8080
  PAST_PORT=8888
fi

echo ">"
echo "> application.jar 교체"
IDLE_APPLICATION=$IDLE_PROFILE-doran-doran-1.0.1-SNAPSHOT.jar
IDLE_APPLICATION_PATH=$DEPLOY_PATH$IDLE_APPLICATION
cp $DEPLOY_PATH$JAR_NAME $IDLE_APPLICATION_PATH

echo ">"
echo "> $IDLE_PROFILE 에서 구동중인 애플리케이션 pid 확인"
IDLE_PID=$(pgrep -f $IDLE_APPLICATION)
if [ -z $IDLE_PID ]; then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -9 $IDLE_PID"
  kill -9 $IDLE_PID
  sleep 5
fi

echo ">"
echo "> $IDLE_PROFILE 배포"
nohup java -jar -Duser.timezone=KST -Dspring.profiles.active=dev -Dspring.profiles.include=$IDLE_PROFILE $IDLE_APPLICATION_PATH 1> /dev/null 2>&1 &

echo ">"
echo "> $IDLE_PROFILE 10초 후 Health Check 시작"
echo "> curl -XGET https://woowacourse.com:$IDLE_PORT/actuator/health"
sleep 50

for retry_count in {1..10}
do
  response=$(curl -XGET https://woowacourse.com:$IDLE_PORT/actuator/health)
  up_count=$(echo $response | grep 'UP' | wc -l)

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

  echo ">Health check 연결 실패. 재시도..."
  sleep 10
done

echo "> 백엔드 실행 완료"

echo ">"
echo "> 기존 서버 종료"
pid=`lsof -t -i :$PAST_PORT`
for i in {1..3}; do
  if [ `lsof -t -i :$PAST_PORT` ]; then
    kill -2 $pid
    echo "기존 서버 중지 시도"
    sleep 3
  fi
done

echo `ps -ef | grep java`

if [ `lsof -t -i :$PAST_PORT` ]; then
  echo "기존 서버 강제 중지"
  kill -9 $pid
fi

exit 0