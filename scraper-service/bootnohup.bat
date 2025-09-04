@echo off
schtasks /create /tn Scraper /tr "\"%CD%\bootnow.bat\"" /sc once /st 00:00 /rl limited
schtasks /run /tn Scraper
timeout /t 2
schtasks /delete /tn Scraper /f