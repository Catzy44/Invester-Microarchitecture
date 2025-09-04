@echo off
schtasks /create /tn Processor /tr "\"%CD%\bootnow.bat\"" /sc once /st 00:00 /rl limited
schtasks /run /tn Processor
timeout /t 2
schtasks /delete /tn Processor /f