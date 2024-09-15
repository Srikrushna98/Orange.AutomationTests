package Pages.login;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import Pages.base.Page;

public class Login extends Page {

    @FindBy(xpath = "//input[@placeholder='Username']")
    private WebElement usernameInput;

    @FindBy(xpath = "//input[@placeholder='Password']")
    private WebElement passwordinput;

    @FindBy(xpath = "//button[normalize-space()='Login']")
    private WebElement loginButton;

    public Login(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(QA_URL);
    }

    public void signIn(String[][] credentials) {
        for (String[] credential : credentials) {
            String username = credential[0];
            String password = credential[1];
            open();
            driver.manage().window().maximize();
            // Wait for the page to load
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            usernameInput.clear();
            usernameInput.sendKeys(username);
            passwordinput.clear();
            passwordinput.sendKeys(password);
            loginButton.click();
            // Wait after login attempt (adjust for your application’s response time)
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            // Add logic here to verify login success or failure
            System.out.println("Attempted login with: " + username);
        }

    }

}
