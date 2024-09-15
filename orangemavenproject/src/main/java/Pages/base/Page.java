package Pages.base;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.paulhammant.ngwebdriver.NgWebDriver;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import static utils.utils.delay;
import static utils.utils.delayWithMilliseconds;

/**
 * The base page for whole pages.
 */
@Slf4j
@Getter
public class Page {

    public static final String QA_URL = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
    private static final String PRE_PROD_URL = "";

    /**
     * The constant datePicker.
     */
    protected static final String DATE_PICKER = "//span[@title='%s']";
    private static final Integer DEFAULT_TIMEOUT = 10;
    public WebDriver driver;
    public NgWebDriver ngDriver;

    private static final String LOADING_LOCATOR = "//div[@class='e-spinner-pane e-spin-show']";
    private static final String GRID_LOADING = "//span[@class='ag-icon ag-icon-loading']";

    /**
     * The Loading element.
     */
    @FindBy(xpath = LOADING_LOCATOR)
    protected WebElement loadingElement;

    /**
     * The Loading element by.
     */
    protected By loadingElementBy = By.xpath(LOADING_LOCATOR);

    /**
     * The gridLoading element.
     */
    @FindBy(xpath = GRID_LOADING)
    protected WebElement gridLoading;

    /**
     * The gridLoading element by.
     */
    protected By loadingElementGrid = By.xpath(GRID_LOADING);

    /**
     * Instantiates a new Page.
     *
     * @param driver the driver
     */
    protected Page(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    protected Page(NgWebDriver ngDriver) {
        this.ngDriver = ngDriver;
        PageFactory.initElements((SearchContext) ngDriver, this);
    }

    /**
     * Gets base url.
     *
     * @return the base url
     *
     * public static String getBaseUrl() { if (isPreProd()) { log.info("ENV: Pre
     * Prod"); return PRE_PROD_URL; } else { log.info("ENV: QA"); return QA_URL;
     * } }
     */
    /**
     * Click on body.
     */
    public void clickOnBody() {
        // getDriver().findElement(By.xpath("//body")).click();
    }

    /**
     * Wait for page loaded.
     */
    public void waitForPageLoaded() {
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT)).until(
                d -> ("complete".equals(((JavascriptExecutor) d).executeScript(
                        "return document.readyState")))
        );
    }

    /**
     * Wait loading disappear.
     */
    public void waitLoadingDisappear() {
        int retries = 1;
        delayWithMilliseconds(1000);
        while (isElementPresent(loadingElementBy) && retries <= 5) {
            delay(1);
            retries++;
        }
    }

    /**
     * Wait Grid loading disappear.
     */
    public void waitGridLoadingDisappear() {
        int retries = 1;
        delayWithMilliseconds(200);
        while (isElementPresent(loadingElementGrid) && retries <= 5) {
            delay(1);
            retries++;
        }
    }

    /**
     * Wait loading disappear.
     *
     * @param timeout the timeout
     */
    public void waitLoadingDisappear(int timeout) {

    }

    /**
     * Wait element to be clickable.
     *
     * @param element the element
     */
    public void waitElementToBeClickable(WebElement element) {
        delayWithMilliseconds(2000);

    }

    /**
     * Wait element to be visible.
     *
     * @param element the element
     */
    public void waitElementToBeVisible(WebElement element) {
        delayWithMilliseconds(2000);
    }

    /**
     * Wait element to be in visible.
     *
     * @param element the element
     */
    public void waitElementToBeInVisible(WebElement element) {
        delayWithMilliseconds(2000);
    }

    /**
     * Find dropdown element web element.
     *
     * @param element the element
     * @return the web element
     */
    public void findDropdownElement(String element) {
        driver.findElement(By.xpath("" + element + "")).click();
    }

    /**
     * Is element present by tag boolean.
     *
     * @param tag the tag
     * @param element the element
     * @return the boolean
     */
    public boolean isElementPresentByTag(String tag, String element) {
        return isElementPresent(By.xpath("//" + tag + "[contains(.,'" + element + "')]"));
    }

    /**
     * Find element by tag web element.
     *
     * @param tag the tag
     * @param element the element
     * @return the web element
     */
    public WebElement findElementByTag(String tag, String element) {
        return driver.findElement(By.xpath("//" + tag + "[contains(.,'" + element
                + "')]"));
    }

    /**
     * Is element present boolean.
     *
     * @param locator the locator
     * @return the boolean
     *
     * public boolean isElementPresent(By locator) { return
     * Utils.isElementPresent(getDriver(), locator); }
     */
    /* Scroll to element.
     *
     * @param element the element
     */
    public void scrollToElement(WebElement element) {
        //Utils.scrollToElement(getDriver(), element);
    }

    /**
     * Click on element and wait when loading disappear.
     *
     * @param webElement the webelement
     */
    public void waitLoadingDisappearAndClickElement(WebElement webElement) {
        try {
            waitElementToBeClickable(webElement);
            webElement.click();
        } catch (org.openqa.selenium.ElementClickInterceptedException
                | org.openqa.selenium.StaleElementReferenceException e) {
            waitLoadingDisappear();
            waitElementToBeClickable(webElement);
            webElement.click();
        }
    }

    public WebElement failureAwareFindElement(String xpath, Long waitTimeSeconds) {
        By locator = By.xpath(xpath);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTimeSeconds));
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return driver.findElement(locator);
    }

    private boolean isElementPresent(By loadingElementGrid) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
