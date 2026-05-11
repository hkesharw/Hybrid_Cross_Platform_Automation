# Hybrid Cross Platform Automation

Simple self-healing automation framework using Selenium, Appium, TestNG, and ExtentReports.

## What this project does
- Web automation using Selenium and ChromeDriver
- Mobile automation using Appium (Android)
- Self-healing locator recovery logic
- Demo dashboard reporting using ExtentReports

## Requirements
- Java JDK 25 installed
- Apache Maven installed
- ChromeDriver executable available for web tests
- Optional: Appium server for mobile tests

## Setup
1. Open the project folder:
   ```powershell
   cd "d:\MCA FINAL YEAR\Hybrid_Cross_Platform_Automation"
   ```

2. If you plan to run web tests, provide ChromeDriver:
   - Either set `CHROME_DRIVER_PATH` to the full `chromedriver.exe` path
   - Or place `chromedriver.exe` in the project `drivers\` folder

3. Install dependencies and build:
   ```powershell
   mvn clean compile
   ```

## Run tests
### Run all TestNG tests
```powershell
mvn test
```

### Run only the smoke demo test
```powershell
mvn -Dtest=SmokeTest test
```

### Run the dashboard demo test
```powershell
mvn -Dtest=DemoModeTest test
```

## Notes
- `src/test/java/tests/SmokeTest.java` uses real web browser automation via ChromeDriver.
- `src/test/java/tests/DemoModeTest.java` is a mocked demo suite and works without a real browser.
- If ChromeDriver is missing, set `CHROME_DRIVER_PATH` or add `chromedriver.exe` under `drivers\`.

## Project structure
- `src/main/java` - framework utility classes
- `src/test/java` - test cases
- `drivers/` - browser driver executables
- `pom.xml` - Maven project configuration
