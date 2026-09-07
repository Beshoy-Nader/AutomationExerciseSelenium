package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private By signupLoginButton =
            By.xpath("//a[@href='/login']");

    private By productsButton =
            By.xpath("//a[contains(text(),'Products')]");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean isHomePageDisplayed() {
        return driver.getTitle().contains("Automation Exercise");
    }

    public LoginPage clickSignupLogin() {
        click(signupLoginButton);
        return new LoginPage(driver);
    }

    public ProductsPage clickProducts() {
        click(productsButton);
        return new ProductsPage(driver);
    }
}