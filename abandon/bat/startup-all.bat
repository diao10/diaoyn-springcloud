@echo off

::当前路径地址
set home=%~dp0

start %home%startup-consumer.bat
start %home%startup-provider.bat

echo  Application Start Success