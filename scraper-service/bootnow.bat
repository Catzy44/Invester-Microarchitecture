@echo on
cd /d %~dp0
for /f "tokens=2 delims=," %%i in ('tasklist /v /fo csv ^| findstr /i "INVESTER SCRAPER MICROSERVICE"') do taskkill /pid %%i /f
title INVESTER SCRAPER MICROSERVICE
call gradlew.bat bootRun --console plain --args='--spring.config.location=classpath:/application.properties'
pause