package tests;

import drivers.DriverManager;
import healing.SelfHealingEngine;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class SmokeTest {
    @Test
    public void demoModeLoginHealing() throws Exception {
        System.out.println("[Demo Mode] Starting self-healing login demo...");

        WebDriver driver = DriverManager.getDriver("web");
        driver.get("https://example.com/login");

        // Demo ke liye intentionally wrong locator set kiya hai
        By brokenLocator = By.id("login_button_old_id");

        // Backup attributes: agar ID fail ho jaye toh ye attributes scan kiye jayenge
        Map<String, String> backupAttributes = new HashMap<>();
        backupAttributes.put("text", "Login");
        backupAttributes.put("name", "login");
        backupAttributes.put("placeholder", "Login");

        WebElement healedElement = SelfHealingEngine.findElementWithHealing(driver, brokenLocator, backupAttributes);

        System.out.println("[Demo Mode] Healed element found. Performing action...");
        healedElement.click();

        System.out.println("[Demo Mode] Self-healing successful. Test passed.");
        driver.quit();
    }
}
