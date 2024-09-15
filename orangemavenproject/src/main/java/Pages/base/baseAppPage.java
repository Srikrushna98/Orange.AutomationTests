package Pages.base;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.paulhammant.ngwebdriver.NgWebDriver;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.testng.internal.Utils;

import static utils.utils.delayWithMilliseconds;

/**
 * The for full screen pages with header footer.
 */
@Slf4j
@Getter
public class baseAppPage extends Page {

    /**
     * The Title.
     */
    @FindBy(xpath = "//div[@class='header-item']//h1")
    protected WebElement title;

    /**
     * The User name.
     */
    @FindBy(xpath = "//div[@class='stick-right header-content']/div[@class='header-item']")
    protected WebElement userName;

    /**
     * The Logout button.
     */
    @FindBy(xpath = "//li[contains(.,'LogOut')]")
    protected WebElement logoutButton;

    /**
     * The Table.
     */
    @FindBy(xpath = "//table[@class='e-table']")
    protected WebElement table;

    @FindBy(xpath = "//div[contains(@class,'header-item')]//li[@role='menuitem']")
    private WebElement userMenu;

    /**
     * The Popup message.
     */
    @FindBy(xpath = "//div[@role='alert']")
    protected WebElement popupMessage;

    @FindBy(xpath = "//div[contains(text(), 'Export')]")
    private WebElement exportButton;

    @FindBy(xpath = "//span[@class='e-frame e-icons']")
    private WebElement checkbox;

    @FindBy(xpath = "//table[@class='e-table']/tbody/tr")
    private WebElement checkIfTableIsEmpty;
    @FindBy(xpath = "//span//i-feather[@name='edit']")
    private WebElement editIcon;

    @FindBy(xpath = "//ejs-switch[@formcontrolname='value'][@role='switch']")
    private WebElement toggleBar;

    @FindBy(xpath = "//label[normalize-space()='Turned Off']")
    private WebElement turnOffLabel;

    @FindBy(xpath = "//button[@id='addFormButton'][contains(.,'Save')]")
    private WebElement saveButton;

    @FindBy(xpath = "//button[@id='cancelFormButton']")
    private WebElement cancelButton;

    @FindBy(xpath = "//label[contains(.,'Turned On')]")
    private WebElement turnOnLabel;

    @FindBy(xpath = "/html/body")
    private WebElement webPage;

    // Get text from Toast
    @FindBy(xpath = "//div[@class='e-toast-content']//div")
    private WebElement toastMessage;

    /**
     * Instantiates a new Base app page.
     *
     * @param driver the driver
     */
    protected baseAppPage(WebDriver driver) {
        super(driver);
    }

    protected baseAppPage(NgWebDriver ngDriver) {
        super(ngDriver);
    }

    /**
     * Wait url contains.
     *
     * @param url the url
     * @param timeout the timeout
     */
    public void waitUrlContains() {
        driver.getCurrentUrl();
    }

    /**
     * Get table entity string [ ].
     *
     * @param table the table
     * @return the string [ ]
     */
    public String[] getTableEntity(WebElement table) {
        return table.getText().split(" ");
    }

    /**
     * Gets table column text by index.
     *
     * @param table the table
     * @param index the index
     * @return the table column text by index
     */
    public String getTableColumnTextByIndex(WebElement table, int index) {
        return table.getText().split("\n")[index];
    }

    /**
     * Gets table text.
     *
     * @param table the table
     * @return the table text
     */
    public String getTableText(WebElement table) {
        return table.getText();
    }

    /**
     * Gets table text by row.
     *
     * @param table the table
     * @param rowIndex the row
     * @return the table row text by row
     */
    public List<String> getTableRowTextByRow(WebElement table, int rowIndex) {
        return table.findElements(By.xpath("//tbody/tr[" + rowIndex + "]/td"))
                .stream().map(WebElement::getText).filter(td -> !td.isEmpty()).toList();
    }

    /**
     * Logout from application.
     */
    public void logout() {
        userMenu.click();
        delayWithMilliseconds(120);
        logoutButton.click();
        // waitUrlContains("login", 45);
    }

    public void randomSelect(int numberOfItemToBeSelected) {
        for (int i = 0; i < numberOfItemToBeSelected; i++) {
            if (!checkIfTableIsEmpty.getAttribute("Class").contains("e-emptyrow")) {
                checkbox.click();
            }
        }
    }

    public void verifyListOfString(List<String> actualData, List<String> expectedData) {
        Assert.assertTrue(actualData.containsAll(expectedData), "Expected header are not shown in the excel");
    }

    public void setConfigurationON(String configuration) {

        editIcon.click();

        try {
            if (turnOffLabel.isDisplayed()) {
                toggleBar.click();
                waitElementToBeClickable(saveButton);
                saveButton.click();
            }

        } catch (org.openqa.selenium.NoSuchElementException e) {
            waitElementToBeClickable(cancelButton);
            cancelButton.click();
        }
    }

    public void setConfigurationOFF(String configuration) {
        editIcon.click();

        try {
            if (turnOnLabel.isDisplayed()) {
                toggleBar.click();
                waitElementToBeClickable(saveButton);
                saveButton.click();
            }
        } catch (org.openqa.selenium.NoSuchElementException e) {
            waitElementToBeClickable(cancelButton);
            cancelButton.click();
        }
    }

    public void verifyErrorToast(String errorMessage) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(80));
        WebElement errorPopUp = wait
                .until(ExpectedConditions
                        .elementToBeClickable(toastMessage));
        Assert.assertEquals(errorPopUp.getText(), errorMessage, "Error popup is not displayed");

    }

    public void verifyTextFromToastMessage(String toastText) {
        waitElementToBeVisible(toastMessage);
        System.out.println(toastText + " - " + toastMessage.getText());
        Assert.assertTrue(toastText.equals(toastMessage.getText()), "Verified text on PopUp");
        waitElementToBeInVisible(toastMessage);
    }

    public boolean checkIfAscending(List<String> list) {
        List<LocalDate> dates = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate date = LocalDate.parse(list.get(i), DATEFORMATTER);
            dates.add(date);
        }
        boolean ascending = true;
        for (int i = 0; i < dates.size() - 1; i++) {
            if (dates.get(i).isAfter(dates.get(i + 1))) {
                ascending = false;
            }
        }
        return ascending;
    }

    public boolean checkIfDescending(List<String> list) {
        List<LocalDate> dates = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate date = LocalDate.parse(list.get(i), DATEFORMATTER);
            dates.add(date);
        }
        boolean descending = true;
        for (int i = 0; i < dates.size() - 1; i++) {
            if (dates.get(i).isBefore(dates.get(i + 1))) {
                descending = false;
            }
        }
        return descending;
    }

    public void switchtoMainWindow() {
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(0));
    }

    public void switchtoLatestWindow() {
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size() - 1));
    }

    public boolean isControlDisplayed(String xpath) {
        try {
            return driver.findElement(By.xpath(xpath)).isDisplayed();
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean isControlEnabled(String xpath) {
        try {
            return driver.findElement(By.xpath(xpath)).isEnabled();
        } catch (Exception ex) {
            return false;
        }
    }

    public void openNewTab(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("window.open()");
        switchtoLatestWindow();
    }
}
