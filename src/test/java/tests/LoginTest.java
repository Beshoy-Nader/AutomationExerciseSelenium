package tests;

import base.BaseTest;
import data.TestData;
import data.TestDataLoader;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AccountPage;
import pages.HomePage;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    private final TestData testData =
            TestDataLoader.getTestData();

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
                testData.getRegistration().getInvalidEmail(),
                testData.getRegistration().getInvalidPassword()
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
                        testData.getRegistration().getValidEmail(),
                        testData.getRegistration().getPassword()
                );

        // 5. Verify logged in successfully
        Assert.assertTrue(
                accountPage.isLoggedIn(
                        testData.getRegistration().getName()
                ),
                "User is not logged in successfully"
        );
    }
}