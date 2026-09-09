package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductDetailsPage extends BasePage {

    private By productInformation =
            By.cssSelector(".product-information");

    private By quantityInput =
            By.id("quantity");

    private By addToCartButton =
            By.xpath("//button[contains(text(),'Add to cart')]");

    private By addToCartButtonProductDetails =
            By.xpath("//button[normalize-space()='Add to cart']");

    private By viewCartButton =
            By.xpath("//u[contains(text(),'View Cart')]");

    public ProductDetailsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isProductDetailsDisplayed() {
        return isDisplayed(productInformation);
    }

    public void setQuantity(int quantity) {
        driver.findElement(quantityInput).clear();
        type(quantityInput, String.valueOf(quantity));
    }

    public void addToCart() {
        click(addToCartButton);
    }

    public void addToCartProductDetailsPage() {
        click(addToCartButtonProductDetails);
    }

    public CartPage addProductToCartWithQuantity(int quantity) {

        setQuantity(quantity);
        addToCart();

        return new CartPage(driver);
    }

    public CartPage clickViewCart() {

        click(viewCartButton);

        return new CartPage(driver);
    }
}