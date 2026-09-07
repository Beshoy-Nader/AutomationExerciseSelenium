package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.math.BigDecimal;

public class CartPage extends BasePage {

    // Cart products
    private By cartProducts =
            By.cssSelector("#cart_info_table tbody tr");

    // First product
    private By firstProductName =
            By.cssSelector("#product-1 .cart_description h4 a");

    private By firstProductPrice =
            By.cssSelector("#product-1 .cart_price p");

    private By firstProductQuantity =
            By.cssSelector("#product-1 .cart_quantity button");

    private By firstProductTotal =
            By.cssSelector("#product-1 .cart_total_price");

    // Second product
    private By secondProductName =
            By.cssSelector("#product-2 .cart_description h4 a");

    private By secondProductPrice =
            By.cssSelector("#product-2 .cart_price p");

    private By secondProductQuantity =
            By.cssSelector("#product-2 .cart_quantity button");

    private By secondProductTotal =
            By.cssSelector("#product-2 .cart_total_price");

    // Proceed to checkout
    private By proceedToCheckoutButton =
            By.xpath("//a[contains(text(),'Proceed To Checkout')]");

    private By viewCartButton =
            By.xpath("//u[contains(text(),'View Cart')]");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean areProductsDisplayed() {
        return isDisplayed(cartProducts);
    }

    public String getFirstProductName() {
        return getText(firstProductName);
    }

    public BigDecimal getFirstProductPrice() {
        return getPrice(firstProductPrice);
    }

    public int getFirstProductQuantity() {
        return Integer.parseInt(getText(firstProductQuantity));
    }

    public BigDecimal getFirstProductTotal() {
        return getPrice(firstProductTotal);
    }

    public String getSecondProductName() {
        return getText(secondProductName);
    }

    public BigDecimal getSecondProductPrice() {
        return getPrice(secondProductPrice);
    }

    public int getSecondProductQuantity() {
        return Integer.parseInt(getText(secondProductQuantity));
    }

    public BigDecimal getSecondProductTotal() {
        return getPrice(secondProductTotal);
    }

    public boolean isSecondProductTotalCorrect() {

        BigDecimal price = getSecondProductPrice();

        int quantity = getSecondProductQuantity();

        BigDecimal expectedTotal =
                price.multiply(BigDecimal.valueOf(quantity));

        BigDecimal actualTotal =
                getSecondProductTotal();

        return expectedTotal.compareTo(actualTotal) == 0;
    }

    public CheckoutPage proceedToCheckout() {
        click(proceedToCheckoutButton);

        return new CheckoutPage(driver);
    }

    private BigDecimal getPrice(By locator) {

        String priceText = getText(locator);

        priceText = priceText
                .replace("Rs.", "")
                .replace(",", "")
                .trim();

        return new BigDecimal(priceText);
    }
    public void clickViewCart() {
        click(viewCartButton);
    }
}
