@echo off

:: ����Ƿ��Թ���Ա�������
net session >nul 2>&1
if %errorLevel% == 0 (
    echo �����Թ���Ա�������
    taskkill /F /im java.exe
    taskkill /F /im python.exe
    taskkill /F /im minio.exe
    taskkill /F /im redis-server.exe

) else (
    echo ���Թ���Ա�������
    pause
    exit
)
