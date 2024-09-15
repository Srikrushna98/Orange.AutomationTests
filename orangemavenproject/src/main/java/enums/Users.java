package enums;

import org.openqa.selenium.WebDriver;

import Pages.base.Page;

public class Users extends Page {

    // Credentials stored as a 2D array
    public static String[][] CREDENTIALS = {
        {"Admin", "admin123"}, //{"user2", "password2"},
    //{"user3", "password3"}
    };

    // Constructor to initialize the WebDriver
    public Users(WebDriver driver) {
        super(driver);
    }

    // A method to get user credentials (this can be used as a data provider in TestNG or for looping)
    public String[][] getCredentials() {
        return CREDENTIALS;
    }

    // Optional: Use this method as a Data Provider for TestNG (if you're using TestNG)
    @org.testng.annotations.DataProvider(name = "userCredentials")
    public Object[][] dataProvider() {
        return CREDENTIALS;

    }

}
