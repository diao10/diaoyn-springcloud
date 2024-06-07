#!/bin/bash
#天阳客户关系管理系统通用部署配置
#应用服务路径
#. ~/.bash_profile
BAS_PATH=/home/bhb
#服务包发布地址
APP_PATH=$BAS_PATH/server
#配置文件所在地址
YML_PATH=$BAS_PATH/config
#日志文件所在路径
LOG_PATH=$BAS_PATH/logs
#各环境配置切换 [dev, test, uat, pro]
PROFILE=dev
#备份文件所在地址
BAK_PATH=$BAS_PATH/bak
