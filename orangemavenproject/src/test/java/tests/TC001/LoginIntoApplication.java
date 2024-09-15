package tests.TC001;

import org.testng.annotations.Test;

import common.Steps;
import static enums.Users.CREDENTIALS;
import tests.BaseTest;

public class LoginIntoApplication extends BaseTest {

    @Test(description = "Login Into Application")
    public void TestOrangeHrm_LoginIntoApplication() {

        Steps Steps = new Steps(getWebDriver());
        Steps.loginWithUser(CREDENTIALS);

    }

}
