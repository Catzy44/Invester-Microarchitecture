for /f "tokens=2 delims=," %%i in ('tasklist /v /fo csv ^| findstr /i "INVESTER SCRAPER MICROSERVICE"') do (
  echo Killing PID %%i
  taskkill /pid %%i /f
)
taskkill /f /im undetected_chromedriver.exe
taskkill /f /im chrome.exe