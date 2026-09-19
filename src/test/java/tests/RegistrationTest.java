package tests;

import base.BaseTest;
import data.TestData;
import data.TestDataLoader;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AccountPage;
import pages.HomePage;
import pages.LoginPage;
import pages.SignupPage;
import utils.TestDataGenerator;

public class RegistrationTest extends BaseTest {

    private final TestData testData =
            TestDataLoader.getTestData();

    @Test
    public void registerNewAccountTest() {

        // Generate a unique email for every test execution
        String email = TestDataGenerator.generateUniqueEmail();

        // 1. Open Home Page
        HomePage homePage = new HomePage(driver);

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

        // 4. Enter name and unique email
        SignupPage signupPage = loginPage.signup(
                testData.getRegistration().getName(),
                email
        );

        // 5. Verify ENTER ACCOUNT INFORMATION
        Assert.assertTrue(
                signupPage.isAccountInformationDisplayed(),
                "ENTER ACCOUNT INFORMATION is not displayed"
        );

        // 6. Fill account information
        signupPage.fillAccountInformation(
                testData.getRegistration().getPassword(),
                testData.getRegistration().getBirthDay(),
                testData.getRegistration().getBirthMonth(),
                testData.getRegistration().getBirthYear()
        );

        // 7. Newsletter
        signupPage.subscribeToNewsletter();

        // 8. Special offers
        signupPage.acceptSpecialOffers();

        // 9. Fill address information
        signupPage.fillAddressInformation(
                testData.getRegistration().getFirstName(),
                testData.getRegistration().getLastName(),
                testData.getRegistration().getCompany(),
                testData.getRegistration().getAddress(),
                testData.getRegistration().getAddress2(),
                testData.getRegistration().getCountry(),
                testData.getRegistration().getState(),
                testData.getRegistration().getCity(),
                testData.getRegistration().getZipcode(),
                testData.getRegistration().getMobile()
        );

        // 10. Create Account
        AccountPage accountPage =
                signupPage.clickCreateAccount();

        // 11. Verify ACCOUNT CREATED
        Assert.assertTrue(
                accountPage.isAccountCreated(),
                "ACCOUNT CREATED message is not displayed"
        );

        // 12. Click Continue
        accountPage.clickContinue();

        // 13. Verify Logged in as username
        Assert.assertTrue(
                accountPage.isLoggedIn(
                        testData.getRegistration().getName()
                ),
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

        // 4. Attempt registration with existing email
        loginPage.signup(
                testData.getRegistration().getName(),
                testData.getRegistration().getExistingEmail()
        );

        // 5. Verify duplicate email error
        Assert.assertTrue(
                loginPage.isEmailAlreadyExistsDisplayed(),
                "Email Address already exist! message is not displayed"
        );
    }
}