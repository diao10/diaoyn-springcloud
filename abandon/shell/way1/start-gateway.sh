#!/bin/bash
#这里可替换为所需执行程序名称，其他代码无需更改
APP_NAME="diaoyn-gateway"
#引入通用部署配置
. /home/bhb/shell/common-deploy-cfg.sh
#启动方法
start(){
  is_exist
  if [ $? -eq "0" ]; then
    echo "${APP_NAME} is already running. pid=${pid} ."
  else
	echo "Start deploy ${APP_NAME} application ${PROFILE}"
	#JVM参数配置：【若内存够用可不用此参数，用于-jar参数前】-Xms512m -Xmx512m -XX:PermSize=64m -XX:MaxPermSize=128m JVM 
        #环境切换配置：【在命令.jar文件后添加环境参数】 --spring.profiles.active=${PROFILE}
	#端口号配置：【在命令.jar文件后面添加端口号】 --server.port=8999
	#扫描本地其他路劲配置：--spring.config.locations=${YML_PATH}/服务名/nohup_服务名简写.out
	#输出.out日志 nohup java -jar ... *.jar > ${LOG_OUT_PATH}/nohup_batch.out 2>&1 &
	#不输出.out日志 nohup java -jar ... *.jar > /dev/null 2>&1 &
    nohup java -jar ${APP_PATH}/${APP_NAME}.jar --spring.profiles.active=${PROFILE} --spring.config.location=${YML_PATH}/diaoyn-gateway/bootstrap.yml > ${LOG_PATH}/diaoyn-gateway/diaoyn-gateway.out 2>&1 &
    echo 'Start success active='${PROFILE}' LOG_PATH is '${LOG_PATH}'/diaoyn-gateway/demo-gateway.out'
 fi
}
#引入通用部署工具
. ${BAS_PATH}/shell/common-deploy-tool.sh

