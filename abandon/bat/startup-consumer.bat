@echo off

::关闭弹窗
%1 mshta vbscript:CreateObject("WScript.Shell").Run("%~s0 ::",0,FALSE)(window.close)&&exit

cd ..
::当前路径地址
set home=%cd%\server\
::端口
set port=8406
::jar包名称
set jar_name=demo-service-consumer.jar
::运行环境
set profiles=test

for /f "tokens=1-5" %%i in ('netstat -ano^|findstr ":%port%"') do (
 echo kill the process %%m who use the port
 taskkill /pid %%m -t -f
 goto start
)
:start

::重启,目录最好是英文
 java -jar %home%%jar_name%.jar --spring.profiles.active=%profiles% --server.port=%port%
 echo  Application start...