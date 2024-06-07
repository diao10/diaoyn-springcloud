#!/bin/sh
DIR=$(cd "$(dirname "$0")"; pwd)
PIDFILE="$DIR/server.pid"

if [ !  -f $PIDFILE ] ; then
echo "Application not running..."

else 

echo "stopping Application ..."

PID="$(cat "$PIDFILE")"

kill -9 $PID

rm "$PIDFILE"

echo "...Application stopped"

fi

