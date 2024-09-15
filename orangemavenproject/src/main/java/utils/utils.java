package utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import java.util.*;
import java.time.Duration;
import java.util.function.Consumer;
import org.slf4j.Logger;

import static utils.InitProperties.ENVIRONMENT;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class utils {

    private static final Logger log = LoggerFactory.getLogger(utils.class);

    public static void waitElementIsVanished(WebDriver driver, WebElement element, int timeout) {
        try {
            (new WebDriverWait(driver, Duration.ofSeconds(timeout)))
                    .until(ExpectedConditions.invisibilityOfAllElements(element));
        } catch (Exception e) {
            log.error("waitForElementIsVanished caused exception", e);
        }
    }

    public static void waitElementIsVanished(WebDriver driver, By element, int timeout) {
        try {
            (new WebDriverWait(driver, Duration.ofSeconds(timeout)))
                    .until(ExpectedConditions.invisibilityOfElementLocated(element));
        } catch (Exception e) {
            log.error("waitForElementIsVanished caused exception", e);
        }
    }

    public static void waitElementToBeClickable(WebDriver driver, WebElement element, int timeout) {
        try {
            (new WebDriverWait(driver, Duration.ofSeconds(timeout)))
                    .until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            log.error("waitForElementVisible caused exception", e);
        }
    }

    public static void waitElementToBeClickable(WebDriver driver, By locator, int timeout) {
        try {
            (new WebDriverWait(driver, Duration.ofSeconds(timeout)))
                    .until(ExpectedConditions.elementToBeClickable(locator));
        } catch (Exception e) {
            log.error("waitForElementVisible caused exception", e);
        }
    }

    public static void waitElementToBeVisible(WebDriver driver, WebElement element, int timeout) {
        try {
            (new WebDriverWait(driver, Duration.ofSeconds(timeout)))
                    .until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            log.error("waitForElementVisible caused exception", e);
        }
    }

    public static void waitUrlContains(WebDriver driver, String url, int timeout) {
        try {
            (new WebDriverWait(driver, Duration.ofSeconds(timeout)))
                    .until(ExpectedConditions.urlContains(url));
        } catch (Exception e) {
            log.error("waitForElementVisible caused exception", e);
        }
    }

    public static String getElementValue(WebElement element) {
        return element.getAttribute("value");
    }

    public static void delay(int timeout) {
        try {
            Thread.sleep(timeout * 1_000L);
        } catch (Exception e) {
            log.error("delay caused exception", e);
        }
    }

    public static void delayWithMilliseconds(int timeout) {
        try {
            Thread.sleep(timeout);
        } catch (Exception e) {
            log.error("delay caused exception", e);
        }
    }

    public static void waitPageOpened(WebDriver driver, String url, int timeout) {
        new WebDriverWait(driver, Duration.ofSeconds(timeout))
                .until(arg0 -> driver.getCurrentUrl().contains(url));
    }

    public static boolean isElementPresent(WebDriver driver, By locatorKey) {
        try {
            driver.findElement(locatorKey).isDisplayed();
            return true;
        } catch (org.openqa.selenium.NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    public static void verifyElementAbsentInTable(WebDriver driver, String value) {
        delay(1);
        Assert.assertFalse(isElementPresent(driver, By.xpath("//td[contains(.,'" + value + "')]")));
    }

    public static void javaScriptClick(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public static void scrollToElement(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", element);
    }

    public static void hoverOnElement(WebDriver driver, WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element).perform();
    }

    public static int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public static boolean isSortedList(List<String> listOfStrings) {
        if (listOfStrings.size() == 0 || listOfStrings.size() == 1) {
            return true;
        }
        Iterator<String> iter = listOfStrings.iterator();
        String current, previous = iter.next();
        while (iter.hasNext()) {
            current = iter.next();
            if (previous.compareTo(current) > 0) {
                return false;
            }
            previous = current;
        }
        return true;
    }

    public static boolean isSortedWebElement(List<WebElement> listOfWebElements) {
        List<String> listOfStrings = new ArrayList<String>();
        for (WebElement w : listOfWebElements) {
            System.out.println(w.getText());
            listOfStrings.add(w.getText());
        }
        return isSortedList(listOfStrings);
    }

    public static WebElement getWebElement(WebDriver driver, By locatorKey) {
        return driver.findElement(locatorKey);
    }

    public static String getElementTxt(WebDriver driver, String xpath) {
        WebElement w = driver.findElement(By.xpath(xpath));
        return w.getText();
    }

    public static void zoomOut(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("document.body.style.zoom='50%';");
    }

    public static void verifyElementPresentInTable(WebDriver driver, String value) {
        delay(1);
        Assert.assertTrue(isElementPresent(driver, By.xpath("//td[contains(.,'" + value + "')]")));
    }

    public static boolean isPreProd() {
        return Objects.nonNull(ENVIRONMENT) && ENVIRONMENT.equalsIgnoreCase("pre");
    }

    private static List<Long> RETRY_INTERVAL_MILLIS = List.of(100L, 500L, 1000L, 2000L, 5000L);

    public static <TInput> void executeWithRetry(Consumer<TInput> funcToExecute, TInput input) {
        for (int i = 0; i < RETRY_INTERVAL_MILLIS.size(); ++i) {
            try {
                funcToExecute.accept(input);
                return;
            } catch (Throwable t) {
                System.out.println(
                        "Function Execution failed on attempt " + i + " :" + ExceptionUtils.getRootCauseMessage(t));
                try {
                    Thread.sleep(RETRY_INTERVAL_MILLIS.get(i));
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    public static void executeWithRetry(Runnable execute) {
        for (int i = 0; i < RETRY_INTERVAL_MILLIS.size(); ++i) {
            try {
                execute.run();
                return;
            } catch (Throwable t) {
                if (i == RETRY_INTERVAL_MILLIS.size()) {
                    throw t;
                } else {
                    try {
                        Thread.sleep((Long) RETRY_INTERVAL_MILLIS.get(i));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public static boolean isTextPresentInList(List<WebElement> listOfWebElements, String text) {
        /// List<String> listOfStrings = new ArrayList<String>();
        for (WebElement w : listOfWebElements) {
            if (w.getText().toLowerCase().contains(text.toLowerCase())) {

                return true;

            }
        }

        return false;
    }

    public static String getTextWithAttributes(WebElement elm) {

        String elmTxt = null;
        elmTxt = elm.getText();
        if (elmTxt.equals("")) {

            elmTxt = elm.getAttribute("innerText");
            if (elmTxt.equals("")) {
                elmTxt = elm.getAttribute("textContent");

                if (elmTxt.equals("")) {
                    {

                        elmTxt = elm.getAttribute("innerHTML");
                    }
                }
            }

        }
        return elmTxt;
    }

    //For Scrooling the Horizontal Bar //
    public static void scrollPageHorizontalBar(int position, WebDriver driver, WebElement horBar) {
        Actions move = new Actions(driver);
        waitElementToBeClickable(driver, horBar, 3);
        move.moveToElement(horBar).clickAndHold();
        move.moveByOffset(position, 0);
        move.release();
        move.perform();
    }

    //verify numeric Value is  integer or a decimal
    public static boolean VerifyIsNumberContainsDecimal(double num) {
        boolean val = false;

        if (num % 1 != 0) {

            val = true;
            return val;
        }

        return val;
    }

    public static String getElementMaxLenght(WebElement element) {
        return element.getAttribute("maxlength");
    }

}
