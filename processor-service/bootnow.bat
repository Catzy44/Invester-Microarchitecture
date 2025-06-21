@echo off
title INVESTER PROCESSOR MICROSERVICE
call gradlew.bat bootRun --console plain --args='--spring.config.location=classpath:/application.properties'
pause