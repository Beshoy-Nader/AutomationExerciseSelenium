package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountPage extends BasePage {

    private By accountCreatedMessage =
            By.xpath("//*[contains(text(),'ACCOUNT CREATED!')]");

    private By loggedInUser =
            By.xpath("//a[contains(text(),'Logged in as')]");
    private By continueButton =
            By.xpath("//a[contains(text(),'Continue')]");

    public AccountPage(WebDriver driver) {
        super(driver);
    }

    public boolean isAccountCreated() {
        return isDisplayed(accountCreatedMessage);
    }

    public boolean isLoggedIn(String username) {
        return getText(loggedInUser).contains(username);
    }
    public void clickContinue() {
        click(continueButton);
    }
}
