@echo off

:: important - cd script location directory
cd /d %~dp0

:: fetch current desktop user and save into CURR variable
for /f "usebackq delims=" %%U in (`powershell -NoP -C "(Get-CimInstance Win32_ComputerSystem).UserName"`) do set CURR=%%U

:: schtasks /create /tn Processor /tr "\"%CD%\bootnow.bat\"" /sc once /st 00:00 /rl limited /RU %CURR% /it
schtasks /create /tn Processor /tr "cmd /c start /min \"\" \"%CD%\bootnow.bat\"" /sc once /st 00:00 /rl limited /RU %CURR%
schtasks /run /tn Processor
timeout /t 2
schtasks /delete /tn Processor /f