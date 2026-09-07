package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AccountPage;
import pages.HomePage;
import pages.LoginPage;
import utils.TestData;

public class LoginTest extends BaseTest {

    @Test
    public void loginWithInvalidCredentialsTest() {

        HomePage homePage = new HomePage(driver);

        // 1. Click Signup / Login
        LoginPage loginPage = homePage.clickSignupLogin();

        // 2. Verify "Login to your account"
        Assert.assertTrue(
                loginPage.isLoginAccountDisplayed(),
                "Login to your account section is not displayed"
        );

        // 3 + 4. Enter invalid credentials and click Login
        loginPage.login(
                "invalid@email.com",
                "WrongPassword123"
        );

        // 5. Verify error message
        Assert.assertTrue(
                loginPage.isInvalidLoginErrorDisplayed(),
                "Invalid login error message is not displayed"
        );
    }


    @Test
    public void loginWithValidCredentialsTest() {

        HomePage homePage = new HomePage(driver);

        // 1. Click Signup / Login
        LoginPage loginPage = homePage.clickSignupLogin();

        // 2. Verify "Login to your account"
        Assert.assertTrue(
                loginPage.isLoginAccountDisplayed(),
                "Login to your account section is not displayed"
        );

        // 3 + 4. Enter valid credentials and click Login
        AccountPage accountPage =
                loginPage.login(
                        TestData.VALID_EMAIL,
                        TestData.PASSWORD
                );

        // 5. Verify logged in successfully
        Assert.assertTrue(
                accountPage.isLoggedIn(TestData.NAME),
                "User is not logged in successfully"
        );
    }
}