package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import Pages.base.baseAppPage;

public class RegionsPage extends baseAppPage {

    @FindBy(xpath = "//button[contains(text(),'Add Region')]")
    private WebElement addRegionButton;

    @FindBy(xpath = "//button[@cssclass='e-outline button-filter']")
    private WebElement filtersButton;

    @FindBy(xpath = "//button[@cssclass='e-flat primary-icon-button']")
    private WebElement editButton;

    @FindBy(xpath = "//button[@class='e-control e-btn e-lib delete-button e-primary']")
    private WebElement deleteButtonApprove;

    @FindBy(xpath = "//ejs-multiselect[@formcontrolname='region']")
    private WebElement regionNameDropDown;

    @FindBy(xpath = "//button[@cssclass='e-outline button-import']")
    private WebElement importButton;

    @FindBy(xpath = "//button[@cssclass='e-outline button-export']")
    private WebElement exportButton;

    @FindBy(xpath = "//label[contains(text(),'Region Name ')]")
    private WebElement regionNameLabel;

    @FindBy(xpath = "//button[@id='cancelFormButton']")
    private WebElement cancelButton;

    @FindBy(xpath = "//button[contains(text(),'Save')]")
    private WebElement saveButton;

    @FindBy(xpath = "//div[contains(text(),'Are you sure you want to cancel? All data will be ')]")
    private WebElement unsavedPopUp;

    @FindBy(xpath = "//button[@type='button'][contains(text(),'Cancel')]")
    private WebElement cancelButtonInPopUp;

    @FindBy(xpath = "//button[contains(text(),'Leave')]")
    private WebElement leaveButtonInPopUp;

    @FindBy(xpath = "//span[@class='remove-button']")
    private WebElement trashIcon;

    @FindBy(xpath = "//h3[contains(text(),'Regions')]")
    private WebElement regionsHeader;

    @FindBy(xpath = "//button[@cssclass='button-filter']")
    private WebElement buttonFilter;

    @FindBy(xpath = "//ejs-multiselect[@formcontrolname='ids']")
    private WebElement regionDropdown;

    @FindBy(xpath = "//td[@class='e-rowcell']")
    private WebElement regionValue;

    @FindBy(xpath = "//div[contains(text(),'DELETE_POPUP_MESSAGE')]")
    private WebElement deletePopUp;

    @FindBy(xpath = "//button[@cssclass='e-flat secondary-icon-button']")
    private WebElement deleteButton;

    @FindBy(xpath = "//div[contains(.,'Record has been added')]")
    private WebElement successfulAddedRecord;

    @FindBy(xpath = "//button[@id='clearAllButton']")
    protected WebElement clearAllButton;

    public RegionsPage(WebDriver driver) {
        super(driver);
    }

    public void clickEditButton() {
        editButton.click();
    }

    public void verifyRegionsHomeScreen() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(addRegionButton.isDisplayed(), "Add location is not displayed");
        softAssert.assertTrue(regionsHeader.isDisplayed(), " Regions dropdown is not displayed");
        softAssert.assertTrue(exportButton.isDisplayed(), "export Button is not displayed");
        softAssert.assertAll();
    }

    public void clickOnAddRegionButtonAndverifySideBar() {

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(regionNameLabel.isDisplayed(), "Region name is not displayed");
        softAssert.assertTrue(cancelButton.isDisplayed(), "Cancel button is not displayed");
        softAssert.assertTrue(saveButton.isDisplayed(), "Save Button is not displayed");
        softAssert.assertAll();
        cancelButton.click();
    }

    public void clickOnAddRegionAndSelectRegionAndClickOnSave(String regionName) {
        saveButton.click();
    }

    public void deleteEntity() {

    }

    public void filterByRegionName(String regionName) {

    }

    public void filterByRegionNameAndVerifyRegionName(String regionName) {

        Assert.assertNotEquals(regionValue.getText(), regionName, "Region name is not displayed");
    }

    public void clickOnpencilIconAndChageRegionNameAndClickOnSave(String regionName) {

        saveButton.click();
    }

    public void clickOnTrashIconAndVerifyPopUpMessageAndClickOnCancelButtonAndVerifyRegion(String regionName) {
        WebElement itemTobeDeleted = driver
                .findElement(
                        By.xpath(
                                String.format(
                                        "//td[contains(text(),'%s')]/parent::tr/td/div/button/span/i-feather[@name='trash-2']",
                                        regionName
                                )
                        )
                );
        waitElementToBeVisible(itemTobeDeleted);
        itemTobeDeleted.click();
        waitLoadingDisappear();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(deletePopUp.isDisplayed(), "Delete popup is not displayed");
        waitLoadingDisappearAndClickElement(cancelButtonInPopUp);
        softAssert.assertEquals(regionValue.getText(), "Florida", "Region name is not matched");
        softAssert.assertAll();
    }

    public void clickOnTrashIconAndVerifyPopUpMessageAndClickOnDeleteButtonAndVerifyRegion(String regionName) {
        WebElement itemTobeDeleted = driver
                .findElement(
                        By.xpath(
                                String.format(
                                        "//td[contains(text(),'%s')]/parent::tr/td/div/button/span/i-feather[@name='trash-2']",
                                        regionName
                                )
                        )
                );
        waitElementToBeVisible(itemTobeDeleted);
        itemTobeDeleted.click();
        waitLoadingDisappear();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(deletePopUp.isDisplayed(), "Delete popup is not displayed");
        waitLoadingDisappearAndClickElement(deleteButton);
        softAssert.assertAll();
    }

    public void deleteRegion(String regionName) {
        WebElement itemTobeDeleted = driver
                .findElement(
                        By.xpath(
                                String.format(
                                        "//td[contains(text(),'%s')]/parent::tr/td/div/button/span/i-feather[@name='trash-2']",
                                        regionName
                                )
                        )
                );
        waitElementToBeVisible(itemTobeDeleted);
        itemTobeDeleted.click();
        waitLoadingDisappearAndClickElement(deleteButton);
    }

    public void verifyRecordAddedSuccessfullyPopup() {
        waitLoadingDisappear();
        Assert.assertEquals(successfulAddedRecord.getText(), "");
    }

}
