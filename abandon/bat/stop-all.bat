@echo off

::当前路径地址
set home=%~dp0

start %home%stop-consumer.bat
start %home%stop-provider.bat

echo  Application Start Success