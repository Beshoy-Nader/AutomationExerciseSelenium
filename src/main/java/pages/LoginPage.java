package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private By loginEmail =
            By.xpath("//input[@data-qa='login-email']");

    private By loginPassword =
            By.xpath("//input[@data-qa='login-password']");

    private By loginButton =
            By.xpath("//button[@data-qa='login-button']");

    private By signupName =
            By.xpath("//input[@data-qa='signup-name']");

    private By signupEmail =
            By.xpath("//input[@data-qa='signup-email']");

    private By signupButton =
            By.xpath("//button[@data-qa='signup-button']");

    // Verification locators
    private By loginAccountTitle =
            By.xpath("//h2[contains(text(),'Login to your account')]");

    private By newUserSignupTitle =
            By.xpath("//h2[contains(text(),'New User Signup!')]");

    private By invalidLoginError =
            By.xpath("//*[contains(text(),'Your email or password is incorrect!')]");

    private By existingEmailError =
            By.xpath("//*[contains(text(),'Email Address already exist!')]");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // Login actions
    public AccountPage login(String email, String password) {
        type(loginEmail, email);
        type(loginPassword, password);
        click(loginButton);

        return new AccountPage(driver);
    }

    // Signup actions

    public SignupPage signup(String name, String email) {
        type(signupName, name);
        type(signupEmail, email);
        click(signupButton);
        return new SignupPage(driver);
    }

    // Verification methods

    public boolean isLoginAccountDisplayed() {
        return isDisplayed(loginAccountTitle);
    }

    public boolean isNewUserSignupDisplayed() {
        return isDisplayed(newUserSignupTitle);
    }

    public boolean isInvalidLoginErrorDisplayed() {
        return isDisplayed(invalidLoginError);
    }

    public boolean isEmailAlreadyExistsDisplayed() {
        return isDisplayed(existingEmailError);
    }
}