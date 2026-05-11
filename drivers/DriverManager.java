package drivers;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import java.net.URL;

public class DriverManager {
    private static WebDriver driver;

    public static WebDriver getDriver(String platform) throws Exception {
        if (driver == null) {
            if (platform.equalsIgnoreCase("web")) {
                String chromeDriverPath = System.getenv("CHROME_DRIVER_PATH");
                if (chromeDriverPath == null || chromeDriverPath.isBlank()) {
                    chromeDriverPath = "drivers\\chromedriver.exe";
                    System.out.println("[DriverManager] CHROME_DRIVER_PATH not set. Trying relative path: " + chromeDriverPath);
                } else {
                    System.out.println("[DriverManager] Using ChromeDriver from CHROME_DRIVER_PATH: " + chromeDriverPath);
                }

                java.io.File driverFile = new java.io.File(chromeDriverPath);
                if (!driverFile.exists()) {
                    throw new IllegalStateException("ChromeDriver executable not found. " +
                            "Set CHROME_DRIVER_PATH to a valid chromedriver.exe path or place chromedriver.exe in the project 'drivers' folder.");
                }

                ChromeDriverService service = new ChromeDriverService.Builder()
                        .usingDriverExecutable(driverFile)
                        .usingAnyFreePort()
                        .build();

                System.out.println("[DriverManager] Starting ChromeDriver service with executable: " + driverFile.getAbsolutePath());

                ChromeOptions options = new ChromeOptions();
                options.addArguments("--remote-allow-origins=*", "--ignore-certificate-errors");
                driver = new ChromeDriver(service, options);
            } else if (platform.equalsIgnoreCase("mobile")) {
                UiAutomator2Options options = new UiAutomator2Options()
                    .setDeviceName("emulator-5554")
                    .setApp("path/to/your/app.apk");
                driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
            }
        }
        return driver;
    }
}
