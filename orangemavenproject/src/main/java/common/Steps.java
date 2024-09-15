package common;

import org.openqa.selenium.WebDriver;

import Pages.login.Login;
import static utils.utils.delay;
import static utils.utils.delayWithMilliseconds;

/**
 * The type Steps.
 */
public class Steps {

    private final WebDriver driver;

    /**
     * Instantiates a new Steps.
     *
     * @param driver the driver
     */
    public Steps(WebDriver driver) {
        this.driver = driver;

    }

    /**
     * Login with user.
     *
     * @param user the user
     */
    public void loginWithUser(String[][] user) {
        Login loginPage = new Login(driver);
        loginPage.open();
        delayWithMilliseconds(2000);
        loginPage.signIn(user);
        delay(5);
    }

}
