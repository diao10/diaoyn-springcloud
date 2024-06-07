#使用说明，用来提示输入参数
usage() {
    echo "Usage: sh 执行脚本.sh [start|stop|restart|status|bak|rollback]"
    exit 1
}
#检查程序是否在运行
is_exist(){
  pid=`ps -ef | grep $APP_NAME.jar | grep -v grep | awk '{print $2}' `
  #如果不存在返回1，存在返回0     
  if [ -z "${pid}" ]; then
   return 1
  else
    return 0
  fi
}
#停止方法
stop(){
  is_exist
  if [ $? -eq "0" ]; then
    echo "Stop ${APP_NAME} application"
    kill -9 $pid
  else
    echo "${APP_NAME} is not running"
  fi  
}
#!/bin/bash
#输出运行状态
status(){
  is_exist
  if [ $? -eq "0" ]; then
    echo "${APP_NAME} is running. Pid is ${pid}"
  else
    echo "${APP_NAME} is not running."
  fi
}
#备份
bak(){
  cp $APP_PATH/$APP_NAME.jar $BAK_PATH
  echo "bakup $APP_PATH/$APP_NAME.jar $BAK_PATH success"
}
#回滚
rollback(){
  cp $BAK_PATH/$APP_NAME.jar $APP_PATH
  echo "rollback $BAK_PATH/$APP_NAME.jar to $APP_PATH success"
}
#重启
restart(){
  stop
  start
}
#根据输入参数，选择执行对应方法，不输入则执行使用说明
case "$1" in
  "start")
    start
    ;;
  "stop")
    stop
    ;;
  "status")
    status
    ;;
  "bak")
    bak
    ;;
  "rollback")
    rollback
    ;;
  "restart")
    restart
    ;;
  *)
    usage
    ;;
esac
