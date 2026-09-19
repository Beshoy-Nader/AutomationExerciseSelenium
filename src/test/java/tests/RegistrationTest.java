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
import org.testng.annotations.BeforeClass;

public class RegistrationTest extends BaseTest {

    private TestData testData;

    @BeforeClass
    public void setUpTestData() {
        testData = TestDataLoader.getTestData();
    }

    @Test
    public void registerNewAccountTest() {

        // Generate a unique email for every test execution
        String email = TestDataGenerator.generateUniqueEmail();

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
        SignupPage signupPage = loginPage.signup(
                testData.getRegistration().getName(),
                email
        );

        // 6. Verify ENTER ACCOUNT INFORMATION
        Assert.assertTrue(
                signupPage.isAccountInformationDisplayed(),
                "ENTER ACCOUNT INFORMATION is not displayed"
        );

        // 7. Fill account information
        signupPage.fillAccountInformation(
                testData.getRegistration().getPassword(),
                testData.getRegistration().getBirthDay(),
                testData.getRegistration().getBirthMonth(),
                testData.getRegistration().getBirthYear()
        );

        // 8. Newsletter
        signupPage.subscribeToNewsletter();

        // 9. Special offers
        signupPage.acceptSpecialOffers();

        // 10. Fill address information
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

        // 11. Create Account
        AccountPage accountPage =
                signupPage.clickCreateAccount();

        // 12. Verify ACCOUNT CREATED
        Assert.assertTrue(
                accountPage.isAccountCreated(),
                "ACCOUNT CREATED message is not displayed"
        );

        // 13. Click Continue
        accountPage.clickContinue();

        // 14. Verify Logged in as username
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

        // 4 & 5. Enter existing email and click Signup
        loginPage.signup(
                testData.getRegistration().getName(),
                testData.getRegistration().getExistingEmail()
        );

        // 6. Verify duplicate email error
        Assert.assertTrue(
                loginPage.isEmailAlreadyExistsDisplayed(),
                "Email Address already exist! message is not displayed"
        );
    }
}