@echo off
cd /d %~dp0

set /p PID=<proc.pid
taskkill /PID %PID% /F

for /f "tokens=2 delims=," %%i in ('tasklist /v /fo csv ^| findstr /i "INVESTER SCRAPER MICROSERVICE"') do (
  echo Killing PID %%i
  taskkill /pid %%i /f
)

taskkill /f /im undetected_chromedriver.exe

taskkill /f /im chrome.exe

timeout /t 5 /nobreak >nul

echo asdf