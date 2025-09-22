@echo off
cd /d %~dp0
title INVESTER PROCESSOR MICROSERVICE
call gradlew.bat bootRun --console plain --args='--spring.config.location=classpath:/application.properties'
exit