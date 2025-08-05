@echo off
cd /d %~dp0
title INVESTER SCRAPER MICROSERVICE
echo 
call gradlew.bat bootRun --console plain --args='--spring.config.location=classpath:/application.properties'
pause