package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AccountPage;
import pages.HomePage;
import pages.LoginPage;
import pages.SignupPage;
import utils.TestData;

public class RegistrationTest extends BaseTest {

    @Test
    public void registerNewAccountTest() {

        // Generate a unique email for every test execution
        String email = TestData.generateUniqueEmail();

        // Open Home Page
        HomePage homePage = new HomePage(driver);

        // 1. Verify Home Page
        Assert.assertTrue(
                homePage.isHomePageDisplayed(),
                "Home page is not displayed"
        );

        // 2. Click Signup / Login
        LoginPage loginPage = homePage.clickSignupLogin();

        // 3. Verify New User Signup
        Assert.assertTrue(
                loginPage.isNewUserSignupDisplayed(),
                "New User Signup section is not displayed"
        );

        // 4 & 5. Enter name/email and click Signup
        SignupPage signupPage = loginPage.signup(TestData.NAME, email);

        // 6. Verify ENTER ACCOUNT INFORMATION
        Assert.assertTrue(
                signupPage.isAccountInformationDisplayed(),
                "ENTER ACCOUNT INFORMATION is not displayed"
        );

        // 7. Fill account information
        signupPage.fillAccountInformation(
                TestData.NAME,
                email,
                TestData.PASSWORD,
                "10",
                "5",
                "1995"
        );

        // 8. Newsletter
        signupPage.subscribeToNewsletter();

        // 9. Special offers
        signupPage.acceptSpecialOffers();

        // 10. Fill address information
        signupPage.fillAddressInformation(
                TestData.FIRST_NAME,
                TestData.LAST_NAME,
                TestData.COMPANY,
                TestData.ADDRESS,
                TestData.ADDRESS_2,
                TestData.COUNTRY,
                TestData.STATE,
                TestData.CITY,
                TestData.ZIPCODE,
                TestData.MOBILE
        );

        // 11. Create Account
        AccountPage accountPage = signupPage.clickCreateAccount();

        // 12. Verify ACCOUNT CREATED
        Assert.assertTrue(
                accountPage.isAccountCreated(),
                "ACCOUNT CREATED message is not displayed"
        );

        // 13. Click Continue
        accountPage.clickContinue();

        // 14. Verify Logged in as username
        Assert.assertTrue(
                accountPage.isLoggedIn(TestData.NAME),
                "Logged in as username is not displayed"
        );
    }


    @Test
    public void registerUsingExistingEmailTest() {

        HomePage homePage = new HomePage(driver);

        // 1. Verify Home Page
        Assert.assertTrue(
                homePage.isHomePageDisplayed(),
                "Home page is not displayed"
        );

        // 2. Click Signup / Login
        LoginPage loginPage = homePage.clickSignupLogin();

        // 3. Verify New User Signup
        Assert.assertTrue(
                loginPage.isNewUserSignupDisplayed(),
                "New User Signup section is not displayed"
        );

        // 4 & 5. Enter existing email and click Signup
        loginPage.signup(
                TestData.NAME,
                TestData.EXISTING_EMAIL
        );

        // 6. Verify duplicate email error
        Assert.assertTrue(
                loginPage.isEmailAlreadyExistsDisplayed(),
                "Email Address already exist! message is not displayed"
        );
    }
}