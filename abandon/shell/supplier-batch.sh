#!/bin/sh
DIR=$(cd "$(dirname "$0")"; pwd)
PIDFILE="$DIR/server.pid"
LOGFILE="$DIR/logs/stdout.log"
STEP=5
#jar包请联系开发人员提供,请把该sh命令与jar放在同一目录下,测试环境和生产环境需要切换PROFILES
PROFILES="prod"  #开发环境为dev,测试环境为test,生产环境为prod
#参数
#cptImport：表示读取供应商的信息数据
#eventImport：表示读取舆情的信息数据 (可以传递参数，例如：eventImport 2021-10-30 2021-10-30,不传则默认查询昨日数据)
#riskImport：表示读取风险信息的数据接口
#注意如果执行多个命令，需要创建多个shell脚本来执行多个命令
PARA="$1"
PARA1="$1"

if [ $2 ] ; then
  if echo $2 | grep -Eq "[0-9]{4}-[0-9]{2}-[0-9]{2}" && date -d $2 +%Y%m%d > /dev/null 2>&1 ; then
    PARA="$PARA $2"
  else
    echo "Not Format"
    exit
  fi

fi
if [ $3 ] ; then
  if echo $3 | grep -Eq "[0-9]{4}-[0-9]{2}-[0-9]{2}" && date -d $3 +%Y%m%d > /dev/null 2>&1 ; then
    PARA="$PARA $3"
  else
    echo "Not Format"
    exit
  fi
fi

if [ ! "$PARA" ]; then
  echo "Para is Null"
  exit
fi
if [ "$PARA1" != "cptImport" ] && [ "$PARA1" != "eventImport" ] && [ "$PARA1" != "riskImport" ] && [ "$PARA1" != "copyrightImport" ]; then
  echo "Para is Error"
  exit
fi
if [ "$PARA1" == "riskImport" ] || [ "$PARA1" == "copyrightImport" ]; then
  if [ ! $2 ] ; then
    echo "Date is Null"
    exit
  fi
  WEEK=`date -d $2 +%w`
  if [ "$WEEK" == '' ] || [ "$WEEK" != 0 ] ;then
  echo "Not Sunday"
  exit
  fi
fi

#创建日志目录
if [ ! -d "$DIR/logs" ]; then
  mkdir $DIR/logs
fi
#jar包启动
if [ -f $PIDFILE ] &&  [ $(cat $PIDFILE) -gt 0 ] ;  then
  PID="$(cat "$PIDFILE")"
  kill -9 $PID
  rm "$PIDFILE"
fi
nohup java -Xms128m -Xmx2048m -jar $DIR/suppres-batch.jar $PARA --spring.profiles.active=$PROFILES > $LOGFILE 2>&1 & echo $! > $PIDFILE
echo "Application Start..."

#轮训20小时判断执行结果
for ((i=0; i<=72000; i=(i+STEP) )) ; do echo "Application Running..."
if (( i >= 5 )); then
  #先判断成功与否
  if [ -f "$DIR/$PARA1.txt" ]; then
  break
  fi
  #查看线程还在不在
  if [ -f $PIDFILE ] &&  [ $(cat $PIDFILE) -gt 0 ] ;  then
  PID="$(cat "$PIDFILE")"
  PROC=`ps -ef | grep $PID | grep -v grep | wc -l`
    if [ $PROC -eq 0 ] ; then
      echo "Application Start Failed"
      exit
    fi
  fi
fi
sleep $STEP
done
#最后删除生成的文件,并停止服务
PID="$(cat "$PIDFILE")"
kill -9 $PID
rm "$PIDFILE"
#日志记录保存
LOGFILE2=$LOGFILE.$PARA1`date +"%Y%m%d%H%M%S"`
mv $LOGFILE $LOGFILE2
#输出
echo "Application Stopped"
if [ -f $DIR/$PARA1.txt ] ;  then
  echo "$(cat "$DIR/$PARA1.txt")"
  rm "$DIR/$PARA1.txt"
fi
exit

