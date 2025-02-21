@echo off

:: 检查是否以管理员身份运行
net session >nul 2>&1
if %errorLevel% == 0 (
    echo 正在以管理员身份运行
    taskkill /F /im java.exe
    taskkill /F /im python.exe
    taskkill /F /im minio.exe
    taskkill /F /im redis-server.exe

) else (
    echo 请以管理员身份运行
    pause
    exit
)
