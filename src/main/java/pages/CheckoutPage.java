package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {

    // Address Details section
    private By addressDetailsTitle =
            By.xpath("//h2[contains(text(),'Address Details')]");

    // Review Your Order section
    private By reviewOrderTitle =
            By.xpath("//h2[contains(text(),'Review Your Order')]");

    // Order comment
    private By commentTextArea =
            By.name("message");

    // Place Order button
    private By placeOrderButton =
            By.xpath("//a[contains(text(),'Place Order')]");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public boolean isAddressDetailsDisplayed() {
        return isDisplayed(addressDetailsTitle);
    }

    public boolean isReviewOrderDisplayed() {
        return isDisplayed(reviewOrderTitle);
    }

    public void enterComment(String comment) {
        type(commentTextArea, comment);
    }

    public PaymentPage clickPlaceOrder() {
        click(placeOrderButton);

        return new PaymentPage(driver);
    }
}
