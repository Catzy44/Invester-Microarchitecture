@echo on
cd /d %~dp0
title INVESTER SCRAPER MICROSERVICE
call gradlew.bat bootRun --console plain --args='--spring.config.location=classpath:/application.properties'
pause