package reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Professional Enterprise-Grade Reporting Manager
 * Handles ExtentReports 5 dashboard generation with custom styling
 */
public class ExtentReportManager {
    private static ExtentReports extentReports;
    private static ExtentSparkReporter sparkReporter;
    private static final String REPORTS_DIRECTORY = "test-reports";
    public static ExtentTest currentTest;

    static {
        createReportsDirectory();
    }

    private static void createReportsDirectory() {
        File directory = new File(REPORTS_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public static void initializeReport(String reportName) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String reportPath = REPORTS_DIRECTORY + "/SelfHealing_Dashboard_" + timestamp + ".html";

        sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setDocumentTitle("Self-Healing Framework - Enterprise Dashboard");
        sparkReporter.config().setReportName("Self-Healing Automation Report");
        sparkReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");

        // Custom CSS for logo and tables
        String customCss = ".logo { font-size: 18px !important; color: #00D9FF !important; font-weight: bold !important; padding: 8px 12px !important; background-color: #2c2c2c !important; border-radius: 4px !important; background-image: none !important; } " +
                          ".logo::before { content: 'SF'; } " +
                          "table { background-color: #1e1e1e !important; color: #e0e0e0 !important; border: 1px solid #555 !important; } " +
                          "th { background-color: #007acc !important; color: #ffffff !important; border: 1px solid #555 !important; } " +
                          "td { background-color: #2d2d2d !important; color: #e0e0e0 !important; border: 1px solid #555 !important; }";
        sparkReporter.config().setCss(customCss);

        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);

        extentReports.setSystemInfo("Framework Name", "Hybrid Cross-Platform Automation Framework");
        extentReports.setSystemInfo("Framework Version", "1.0");
        extentReports.setSystemInfo("Operating System", System.getProperty("os.name") + " " + System.getProperty("os.version"));
        extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
        extentReports.setSystemInfo("Java Vendor", System.getProperty("java.vendor"));
        extentReports.setSystemInfo("User Name", System.getProperty("user.name"));
        extentReports.setSystemInfo("Machine Name", System.getenv("COMPUTERNAME") != null ? System.getenv("COMPUTERNAME") : "Unknown");
        extentReports.setSystemInfo("Thread Count", String.valueOf(Runtime.getRuntime().availableProcessors()));

        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║   SELF-HEALING ENTERPRISE DASHBOARD INITIALIZED                 ║");
        System.out.println("║   Report Location: " + reportPath);
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
    }

    public static ExtentTest createTest(String testName, String description) {
        currentTest = extentReports.createTest(testName, description);
        currentTest.info("Test Case: " + testName);
        return currentTest;
    }

    public static void logInfo(String message) {
        if (currentTest != null) {
            currentTest.info(message);
        }
        System.out.println("ℹ️  [INFO] " + message);
    }

    public static void logWarning(String message) {
        if (currentTest != null) {
            currentTest.warning(message);
        }
        System.out.println("⚠️  [WARNING] " + message);
    }

    public static void logError(String message) {
        if (currentTest != null) {
            currentTest.fail(message);
        }
        System.err.println("❌ [ERROR] " + message);
    }

    public static void logPass(String message) {
        if (currentTest != null) {
            currentTest.pass(message);
        }
        System.out.println("✅ [PASS] " + message);
    }

    public static void logHealed(String message) {
        if (currentTest != null) {
            // Custom HEALED status with highlighting
            currentTest.info("<span style='color: #FF6B35; font-weight: bold;'>🔧 [HEALED] " + message + "</span>");
        }
        System.out.println("🔧 [HEALED] " + message);
    }

    public static void logSectionHeader(String header) {
        if (currentTest != null) {
            currentTest.info("<h4 style='color: #00D9FF; border-bottom: 2px solid #00D9FF; padding-bottom: 8px;'>" + header + "</h4>");
        }
        System.out.println("\n" + "─".repeat(70));
        System.out.println("🔍 " + header);
        System.out.println("─".repeat(70) + "\n");
    }

    public static void logTable(String[] headers, String[][] data) {
        if (currentTest != null) {
            StringBuilder htmlTable = new StringBuilder("<table style='border-collapse: collapse !important; width: 100% !important; margin: 10px 0 !important; background-color: #1e1e1e !important; color: #e0e0e0 !important; border: 1px solid #555 !important;'>");
            htmlTable.append("<thead><tr style='background-color: #007acc !important;'>");
            for (String header : headers) {
                htmlTable.append("<th style='border: 1px solid #555 !important; padding: 8px !important; text-align: left !important; color: #ffffff !important; font-weight: bold !important;'>")
                        .append(header).append("</th>");
            }
            htmlTable.append("</tr></thead><tbody>");

            for (String[] row : data) {
                htmlTable.append("<tr style='background-color: #2d2d2d !important;'>");
                for (String cell : row) {
                    htmlTable.append("<td style='border: 1px solid #555 !important; padding: 8px !important; color: #e0e0e0 !important;'>").append(cell).append("</td>");
                }
                htmlTable.append("</tr>");
            }
            htmlTable.append("</tbody></table>");
            currentTest.info(htmlTable.toString());
        }
    }

    public static void flushReport() {
        if (extentReports != null) {
            extentReports.flush();
            System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
            System.out.println("║   ✅ REPORT GENERATION COMPLETE                                 ║");
            System.out.println("║   Opening dashboard in default browser...                      ║");
            System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
            openReportInBrowser();
        }
    }

    private static void openReportInBrowser() {
        try {
            File reportsDir = new File(REPORTS_DIRECTORY);
            File[] files = reportsDir.listFiles((dir, name) -> name.startsWith("SelfHealing_Dashboard_") && name.endsWith(".html"));
            if (files != null && files.length > 0) {
                File latestReport = files[files.length - 1];
                String reportPath = latestReport.getAbsolutePath();
                System.out.println("📊 Report Path: " + reportPath);

                // Open in browser based on OS
                String osName = System.getProperty("os.name").toLowerCase();
                ProcessBuilder pb;
                if (osName.contains("win")) {
                    pb = new ProcessBuilder("cmd", "/c", "start", reportPath);
                } else if (osName.contains("mac")) {
                    pb = new ProcessBuilder("open", reportPath);
                } else {
                    pb = new ProcessBuilder("xdg-open", reportPath);
                }
                pb.start();
                Thread.sleep(2000); // Allow browser to open
            }
        } catch (Exception e) {
            System.err.println("Could not open report in browser: " + e.getMessage());
        }
    }

    public static ExtentReports getInstance() {
        return extentReports;
    }
}
