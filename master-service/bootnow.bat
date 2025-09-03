@echo off
title INVESTER MASTER SERVICE
call gradlew.bat bootRun --console plain --args='--spring.config.location=classpath:/application.properties'
pause