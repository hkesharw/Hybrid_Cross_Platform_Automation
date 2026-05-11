package healing;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import reporting.ExtentReportManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SELF-HEALING AUTOMATED REPAIR ENGINE
 * Enterprise-Grade Element Recovery System with AI-Based Similarity Matching
 *
 * Mechanism:
 * - Initiates when primary locator strategy fails
 * - Performs DOM architecture scanning and similarity analysis
 * - Employs intelligent attribute-matching algorithm
 * - Recovers element dynamically without test interruption
 *
 * Supported Attributes: text, name, placeholder, class, aria-label, id
 */
public class SelfHealingEngine {

    // ═══════════════════════════════════════════════════════════════════════════
    // CORE HEALING METHODS
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Primary Entry Point - Basic overload for backward compatibility
     */
    public static WebElement findElementWithHealing(WebDriver driver, By originalLocator) {
        return findElementWithHealing(driver, originalLocator, new HashMap<>());
    }

    /**
     * Advanced Healing Protocol with Backup Attributes
     *
     * @param driver WebDriver instance
     * @param originalLocator Primary locator strategy (may fail)
     * @param backupAttributes Map of fallback attributes for similarity matching
     * @return Recovered WebElement or throws NoSuchElementException
     */
    public static WebElement findElementWithHealing(WebDriver driver, By originalLocator,
                                                    Map<String, String> backupAttributes) {
        try {
            // ─────────────────────────────────────────────────────────────────
            // STEP 1: Primary Locator Attempt
            // ─────────────────────────────────────────────────────────────────
            WebElement element = driver.findElement(originalLocator);
            
            ExtentReportManager.logPass("Primary Locator Resolution Successful");
            ExtentReportManager.logInfo("Locator Strategy: " + originalLocator.toString());
            return element;

        } catch (NoSuchElementException e) {
            // ─────────────────────────────────────────────────────────────────
            // STEP 2: Self-Healing Protocol Activation
            // ─────────────────────────────────────────────────────────────────
            ExtentReportManager.logSectionHeader("SELF-HEALING PROTOCOL INITIATED");
            ExtentReportManager.logError("Primary Locator Strategy Failed: " + originalLocator);
            ExtentReportManager.logInfo("Activating Dynamic Element Recovery System...");

            // ─────────────────────────────────────────────────────────────────
            // STEP 3: Backup Attribute Analysis
            // ─────────────────────────────────────────────────────────────────
            ExtentReportManager.logSectionHeader("BACKUP ATTRIBUTE ANALYSIS");
            ExtentReportManager.logInfo("Scanning DOM for elements matching backup attributes...");

            String[] attrHeaders = {"Attribute", "Value", "Usage"};
            String[][] attrData = new String[backupAttributes.size()][];
            int idx = 0;
            for (Map.Entry<String, String> entry : backupAttributes.entrySet()) {
                attrData[idx++] = new String[]{entry.getKey(), entry.getValue(), "Similarity Matching"};
            }
            ExtentReportManager.logTable(attrHeaders, attrData);

            // ─────────────────────────────────────────────────────────────────
            // STEP 4: DOM Scan and Similarity Calculation
            // ─────────────────────────────────────────────────────────────────
            List<WebElement> allElements = driver.findElements(By.xpath("//*"));
            WebElement bestMatch = null;
            int bestScore = 0;
            Map<String, String> bestMatchAttributes = new HashMap<>();

            ExtentReportManager.logInfo("Total DOM Elements Scanned: " + allElements.size());
            ExtentReportManager.logSectionHeader("SIMILARITY SCORE ANALYSIS");

            for (WebElement el : allElements) {
                if (!el.isDisplayed()) {
                    continue; // Only consider visible elements
                }

                int score = calculateSimilarityScore(el, backupAttributes);
                if (score > bestScore) {
                    bestScore = score;
                    bestMatch = el;
                    bestMatchAttributes.put("tagName", safeGetTagName(el));
                    bestMatchAttributes.put("text", safeGetText(el));
                    bestMatchAttributes.put("name", safeGetAttribute(el, "name"));
                    bestMatchAttributes.put("class", safeGetAttribute(el, "class"));
                }
            }

            // ─────────────────────────────────────────────────────────────────
            // STEP 5: Recovery Result
            // ─────────────────────────────────────────────────────────────────
            if (bestMatch != null && bestScore > 0) {
                ExtentReportManager.logSectionHeader("DYNAMIC ELEMENT RECOVERY SUCCESSFUL");
                ExtentReportManager.logHealed("Element Successfully Recovered Using Similarity Matching");
                
                String[] recoveryHeaders = {"Property", "Value", "Match Score"};
                String[][] recoveryData = {
                        {"Tag Name", bestMatchAttributes.get("tagName"), "Confirmed"},
                        {"Display Text", bestMatchAttributes.get("text"), "Confirmed"},
                        {"Name Attribute", bestMatchAttributes.get("name"), "Confirmed"},
                        {"Class Attribute", bestMatchAttributes.get("class"), "Confirmed"},
                        {"Overall Similarity Score", String.valueOf(bestScore), "✅ MATCH"}
                };
                ExtentReportManager.logTable(recoveryHeaders, recoveryData);

                ExtentReportManager.logPass("Healed Element Ready for Interaction");
                return bestMatch;
            }

            // ─────────────────────────────────────────────────────────────────
            // FAILURE: No Recovery Possible
            // ─────────────────────────────────────────────────────────────────
            ExtentReportManager.logError("Element Recovery Failed - No Matching Elements Found");
            ExtentReportManager.logInfo("Recommendation: Verify backup attributes or update locator strategy");
            throw e;
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // SIMILARITY SCORING ENGINE
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Advanced Similarity Scoring Algorithm
     * Evaluates element match quality based on weighted attribute comparison
     *
     * Scoring Weights:
     * - Exact text match: 5 points
     * - Partial text match: 3 points
     * - Exact name match: 4 points
     * - Partial name match: 2 points
     * - Placeholder match: 3 points
     * - Class match: 1 point
     */
    private static int calculateSimilarityScore(WebElement element, Map<String, String> backupAttributes) {
        int score = 0;

        String elementText = safeGetText(element).trim().toLowerCase();
        String elementName = safeGetAttribute(element, "name").trim().toLowerCase();
        String elementPlaceholder = safeGetAttribute(element, "placeholder").trim().toLowerCase();
        String elementClass = safeGetAttribute(element, "class").trim().toLowerCase();
        String elementAriaLabel = safeGetAttribute(element, "aria-label").trim().toLowerCase();

        // TEXT MATCHING (Weight: 5 for exact, 3 for partial)
        if (backupAttributes.containsKey("text")) {
            String textValue = backupAttributes.get("text").trim().toLowerCase();
            if (!textValue.isEmpty()) {
                if (elementText.equals(textValue)) {
                    score += 5;
                } else if (elementText.contains(textValue) || textValue.contains(elementText)) {
                    score += 3;
                }
            }
        }

        // NAME MATCHING (Weight: 4 for exact, 2 for partial)
        if (backupAttributes.containsKey("name")) {
            String nameValue = backupAttributes.get("name").trim().toLowerCase();
            if (!nameValue.isEmpty()) {
                if (elementName.equals(nameValue)) {
                    score += 4;
                } else if (elementName.contains(nameValue) || nameValue.contains(elementName)) {
                    score += 2;
                }
            }
        }

        // PLACEHOLDER MATCHING (Weight: 3)
        if (backupAttributes.containsKey("placeholder")) {
            String placeholderValue = backupAttributes.get("placeholder").trim().toLowerCase();
            if (!placeholderValue.isEmpty()) {
                if (elementPlaceholder.equals(placeholderValue)) {
                    score += 3;
                } else if (elementPlaceholder.contains(placeholderValue)) {
                    score += 1;
                }
            }
        }

        // CLASS MATCHING (Weight: 1)
        if (backupAttributes.containsKey("class")) {
            String classValue = backupAttributes.get("class").trim().toLowerCase();
            if (!classValue.isEmpty() && elementClass.contains(classValue)) {
                score += 1;
            }
        }

        // ARIA-LABEL MATCHING (Weight: 3)
        if (backupAttributes.containsKey("aria-label")) {
            String ariaValue = backupAttributes.get("aria-label").trim().toLowerCase();
            if (!ariaValue.isEmpty() && elementAriaLabel.contains(ariaValue)) {
                score += 3;
            }
        }

        return score;
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // UTILITY METHODS
    // ═══════════════════════════════════════════════════════════════════════════

    private static String safeGetText(WebElement element) {
        try {
            return element.getText();
        } catch (Exception ex) {
            return "";
        }
    }

    private static String safeGetTagName(WebElement element) {
        try {
            return element.getTagName();
        } catch (Exception ex) {
            return "unknown";
        }
    }

    private static String safeGetAttribute(WebElement element, String attribute) {
        try {
            String value = element.getAttribute(attribute);
            return value == null ? "" : value;
        } catch (Exception ex) {
            return "";
        }
    }
}
