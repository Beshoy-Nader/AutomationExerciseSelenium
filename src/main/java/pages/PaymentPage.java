package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PaymentPage extends BasePage {

    private By nameOnCardInput =
            By.name("name_on_card");

    private By cardNumberInput =
            By.name("card_number");

    private By cvcInput =
            By.name("cvc");

    private By expirationMonthInput =
            By.name("expiry_month");

    private By expirationYearInput =
            By.name("expiry_year");

    private By payAndConfirmOrderButton =
            By.id("submit");

    public PaymentPage(WebDriver driver) {
        super(driver);
    }

    public void enterNameOnCard(String name) {
        type(nameOnCardInput, name);
    }

    public void enterCardNumber(String cardNumber) {
        type(cardNumberInput, cardNumber);
    }

    public void enterCvc(String cvc) {
        type(cvcInput, cvc);
    }

    public void enterExpirationMonth(String month) {
        type(expirationMonthInput, month);
    }

    public void enterExpirationYear(String year) {
        type(expirationYearInput, year);
    }

    public OrderConfirmationPage payAndConfirmOrder() {
        click(payAndConfirmOrderButton);
        return new OrderConfirmationPage(driver);
    }

    public void enterPaymentDetails(
            String nameOnCard,
            String cardNumber,
            String cvc,
            String expirationMonth,
            String expirationYear) {

        enterNameOnCard(nameOnCard);
        enterCardNumber(cardNumber);
        enterCvc(cvc);
        enterExpirationMonth(expirationMonth);
        enterExpirationYear(expirationYear);
    }
}
