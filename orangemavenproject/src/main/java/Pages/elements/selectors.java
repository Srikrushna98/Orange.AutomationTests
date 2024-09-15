package Pages.elements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import Pages.base.Page;

public class selectors extends Page {

    @FindBy(xpath = "//ejs-dropdownlist[@id='organization-agency-dropdown']//span[contains(@class, 'e-search-icon')]")
    private WebElement expander;

    /*
    @FindBy(xpath = "//div[@id='organization-agency-dropdown_popup']//li//p[contains(.,'" + HALLMARK + "')]")
    private WebElement hallmarkAgency;
    @FindBy(xpath = "//div[@id='organization-agency-dropdown_popup']//li//p[contains(.,'" + MSP + "')]")
    private WebElement mspAgency;
     */
    public selectors(WebDriver driver) {
        super(driver);
    }

}
