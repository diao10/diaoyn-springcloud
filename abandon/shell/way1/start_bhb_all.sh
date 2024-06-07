#!/bin/bash

#启动方法 bhb 用户登录 10.1.42.100
echo "需要启动4项服务"

#启动nacos
echo "开始启动nacos"
cd /home/bhb/server/nacos/bin
sh /home/bhb/server/nacos/bin/startup.sh -m standalone
sleep 10
echo "nacos启动完成"
echo "已启动1个服务"

#后台管理系统（必须启动nacos）
echo "开始启动后台管理"
sh /home/bhb/shell/start-gateway.sh start
sh /home/bhb/shell/start-provider.sh start
sh /home/bhb/shell/start-consumer.sh start
echo "后台管理启动完成"
echo "已启动4个服务"


is_success(){
  pcount=`ps -ef|grep java | grep -v grep | grep -v sh  | awk '{print $1}' | grep -Po 'bhb' | awk '{sum[$1]+=1} END {for(k in sum) print k "_" sum[k]}'`
  echo "$pcount"
  #如果不存在返回1，存在返回0     
  if [ "bhb_10" == "${pcount}" ]; then
    echo "服务启动成功"
    return 1
  else
    echo "服务启动有误结果是$pcount"
    return 0
  fi
}

is_success
