package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OrderConfirmationPage extends BasePage {

    private By orderConfirmedMessage =
            By.xpath("//p[normalize-space()='Congratulations! Your order has been confirmed!']");

    private By downloadInvoiceButton =
            By.xpath("//a[contains(text(),'Download Invoice')]");

    public OrderConfirmationPage(WebDriver driver) {
        super(driver);
    }

    public boolean isOrderPlacedSuccessfully() {
        return isDisplayed(orderConfirmedMessage);
    }

    public void downloadInvoice() {
        click(downloadInvoiceButton);
    }
}
