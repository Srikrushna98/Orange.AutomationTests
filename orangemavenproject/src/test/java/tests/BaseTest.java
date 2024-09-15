package tests;

import common.Steps;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.LoggerFactory;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import com.paulhammant.ngwebdriver.NgWebDriver;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.*;
import static utils.InitProperties.REMOTE_URL;
import static utils.InitProperties.RUN_TYPE;
import org.slf4j.Logger;

/**
 * The Base class for whole tests.
 */
@Slf4j
public abstract class BaseTest implements IHookable {

    public final ThreadLocal<String> sessionId = new ThreadLocal<>();
    private final ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();
    private final ThreadLocal<NgWebDriver> ngDriver = new ThreadLocal<>();
    private final ThreadLocal<Steps> steps = new ThreadLocal<>();
    public static final String PROJECT_FOLDER_EXPORT = System.getProperty("user.dir") + "\\ExportedFiles";
    public static final String PROJECT_FOLDER_IMPORT = System.getProperty("user.dir") + "\\ImportedFiles";
    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);

    /**
     * Add screenshots for failed skipped and tests with error
     */
    @Override
    public void run(IHookCallBack callBack, ITestResult testResult) {
        callBack.runTestMethod(testResult);
        if (testResult.getThrowable() != null || testResult.getStatus() == 2 || testResult.getStatus() == 3) {
            try {
                if (Objects.nonNull(getWebDriver())) {
                    addScreenshot(testResult);
                }
                log.error("Failed: {}", testResult.getName());
                log.error(testResult.getMethod().getDescription());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Make screenshot and save it into 'screenshots' folder in the root
     */
    private void addScreenshot(ITestResult testResult) throws IOException {
        String parameter = checkDataProvider(testResult);
        String reason = testResult.getThrowable() != null ? "EXCEPTION"
                : testResult.getStatus() == 2 ? "FAILURE" : "SKIP";
        final File screenshot = ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File("./screenshots/" + testResult.getMethod().getMethodName() + "_"
                + reason + parameter + ".png"));
    }

    private String checkDataProvider(ITestResult testResult) {
        String par = "_";
        Object[] parameters = testResult.getParameters();
        if (parameters.length > 0) {
            par = par + parameters[0].toString();
        }
        return par;
    }

    /**
     * Sets up.
     *
     * @throws MalformedURLException the malformed url exception
     */
    @BeforeMethod(alwaysRun = true)
    protected void setUp() throws MalformedURLException {
        setCustomDriver();
        steps.set(new Steps(getWebDriver()));
        new File("./ExportedFiles/").mkdirs();
    }

    /**
     * Tear down.
     */
    @AfterMethod(alwaysRun = true)
    protected void tearDown() {
        if (Objects.nonNull(webDriver.get())) {
            webDriver.get().quit();
        }
        log.info("Driver closed");
    }

    /**
     * Gets web driver.
     *
     * @return the web driver
     */
    public WebDriver getWebDriver() {
        return webDriver.get();
    }

    public NgWebDriver getNgWebDriver() {
        return ngDriver.get();
    }

    /**
     * Gets steps.
     *
     * @return the steps
     */
    public Steps getSteps() {
        return steps.get();
    }

    /**
     * Initialize web driver
     */
    private void setCustomDriver() throws MalformedURLException {
        boolean useLocalBrowser = "local".equalsIgnoreCase(RUN_TYPE);
        ChromeOptions options = createChromeOptions();
        // SA 1 line added for the new chrome version
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver;
        //// options.addArguments("--remote-allow-origins=*");
        if (!useLocalBrowser && Objects.nonNull(REMOTE_URL) && RUN_TYPE.equalsIgnoreCase("remote")) {
            log.info("Remote driver initialization");
            driver = new RemoteWebDriver(new URL(REMOTE_URL), options);
            driver.manage().window().setSize(new Dimension(1920, 1080));
        } else {
            log.info("Local driver initialization");
            // System.setProperty("webdriver.chrome.driver", "C:/Drivers/chrome") ;
            WebDriverManager.chromedriver().clearDriverCache().setup();
            driver = WebDriverManager.chromedriver().capabilities(options).create();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        sessionId.set(((RemoteWebDriver) driver).getSessionId().toString());
        webDriver.set(driver);
        log.info("Driver initialization finished");
    }

    /**
     * Generate capabilities for Chrome web driver
     */
    private ChromeOptions createChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-infobars");
        options.addArguments("window-size=1920,1080");
        options.addArguments("--incognito");
        // options.setCapability("selenoid:options", Map.<String, Object>of("enableVNC",
        // true,"enableVideo", true));
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("download.default_directory", PROJECT_FOLDER_IMPORT);
        chromePrefs.put("download.prompt_for_download", false);
        chromePrefs.put("profile.default_content_settings.popups", 0);
        options.setExperimentalOption("prefs", chromePrefs);
        return options;
    }

    /**
     * Generate random email string ok.
     *
     * @return the string
     */
    public String generateRandomEmail() {
        return "test_aqc@" + RandomStringUtils.randomAlphabetic(5) + ".com";
    }

}
