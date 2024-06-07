#!/bin/bash

#停止方法 bhb 用户登录
echo "需要停止4项服务"

#停止nacos
echo "开始停止nacos"
cd /home/bhb/server/nacos/bin
sh /home/bhb/server/nacos/bin/shutdown.sh
#sleep 10
echo "nacos停止完成"
echo "已停止1个服务"

#后台管理系统（必须停止nacos）
echo "开始停止后台管理"
sh /home/bhb/shell/start-gateway.sh stop
sh /home/bhb/shell/start-provider.sh stop
sh /home/bhb/shell/start-consumer.sh stop
echo "后台管理停止完成"
echo "已停止4个服务"



is_success(){
  pcount=`ps -ef|grep java | grep -v grep | grep -v sh  | awk '{print $1}' | grep -Po 'bhb' | awk '{sum[$1]+=1} END {for(k in sum) print k "_" sum[k]}'`
  echo "pcount结果为【$pcount】"
  #如果不存在返回1，存在返回0     
  if [ -z "${pcount}" ]; then
    echo "服务停止成功"
    return 1
  else
    echo "服务停止有误结果是$pcount"
    return 0
  fi
}
sleep 3
is_success
