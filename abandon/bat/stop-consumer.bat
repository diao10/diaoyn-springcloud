@echo off

::关闭弹窗
%1 mshta vbscript:CreateObject("WScript.Shell").Run("%~s0 ::",0,FALSE)(window.close)&&exit

::端口
set port=8406

for /f "tokens=1-5" %%i in ('netstat -ano^|findstr ":%port%"') do (
 echo kill the process %%m who use the port
 taskkill /pid %%m -t -f
 goto start
)
:start
