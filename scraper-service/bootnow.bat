@echo off
cd /d %~dp0
title INVESTER SCRAPER MICROSERVICE
call gradlew.bat bootRun --args='--spring.config.location=classpath:/application.properties'