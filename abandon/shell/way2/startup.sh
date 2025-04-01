#!/bin/sh
DIR=$(cd "$(dirname "$0")"; pwd)
PIDFILE="$DIR/server.pid"
LOGFILE="$DIR/logs/stdout.log"
JARFILE=$(dirname "$DIR")
CONFIGPATH="$HOME/config/consumer/"

if [ -f $PIDFILE ] &&  [ $(cat $PIDFILE) -gt 0 ] ;  then
echo "Application is already running..."
exit 1
fi 
nohup java -Xms256m -Xmx2048m -jar $JARFILE/diaoyn-service-consumer.jar --spring.profiles.active=prod --spring.config.location=$CONFIGPATH  > $LOGFILE 2>&1 & echo $! > $PIDFILE
echo "Application start..."

