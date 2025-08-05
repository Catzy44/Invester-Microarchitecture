taskkill /f /im undetected_chromedriver.exe
for /f "tokens=2 delims=," %%i in ('tasklist /v /fo csv ^| findstr /i "INVESTER SCRAPER MICROSERVICE"') do taskkill /pid %%i /f