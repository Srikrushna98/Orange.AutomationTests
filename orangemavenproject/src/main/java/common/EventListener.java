package common;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

import static utils.utils.delay;

public class EventListener implements WebDriverListener {

    @Override
    public void beforeClick(WebElement element) {
        delay(1);
    }

    @Override
    public void beforeSendKeys(WebElement element, CharSequence... keysToSend) {
        delay(1);
    }

    @Override
    public void beforeFindElement(WebDriver d, By by) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) d;
            WebElement e;
            e = d.findElement(by);
            js.executeScript("arguments[0].style.border='2px dashed green'", e);
        } catch (Exception ex) {
        }
    }
}
