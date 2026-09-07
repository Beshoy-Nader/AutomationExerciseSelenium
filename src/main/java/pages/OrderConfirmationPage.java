package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OrderConfirmationPage extends BasePage {

    private By orderPlacedMessage =
            By.xpath("//*[contains(text(),'Your order has been placed successfully!')]");

    private By downloadInvoiceButton =
            By.xpath("//a[contains(text(),'Download Invoice')]");

    public OrderConfirmationPage(WebDriver driver) {
        super(driver);
    }

    public boolean isOrderPlacedSuccessfully() {
        return isDisplayed(orderPlacedMessage);
    }

    public void downloadInvoice() {
        click(downloadInvoiceButton);
    }
}
