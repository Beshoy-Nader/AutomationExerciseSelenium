package pages;


import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductsPage extends BasePage {

    private By allProductsTitle =
            By.xpath("//h2[contains(text(),'All Products')]");

    private By searchInput =
            By.id("search_product");

    private By searchButton =
            By.id("submit_search");

    private By searchedProductsTitle =
            By.xpath("//h2[contains(text(),'Searched Products')]");

    private By firstProduct =
            By.xpath("(//div[contains(@class,'product-image-wrapper')])[1]");

    private By secondProduct =
            By.xpath("(//div[contains(@class,'product-image-wrapper')])[2]");

    private By firstAddToCart =
            By.xpath("(//div[contains(@class,'product-image-wrapper')])[1]" +
                    "//a[contains(@class,'add-to-cart')]");

    private By secondViewProduct =
            By.xpath("(//div[contains(@class,'product-image-wrapper')])[2]" +
                    "//a[contains(text(),'View Product')]");
    private By searchResults =
            By.cssSelector(".features_items .product-image-wrapper");

    private By continueShoppingButton =
            By.xpath("//button[contains(text(),'Continue Shopping')]");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isAllProductsDisplayed() {
        return isDisplayed(allProductsTitle);
    }

    public void searchProduct(String productName) {
        type(searchInput, productName);
        click(searchButton);
    }

    public boolean isSearchedProductsDisplayed() {
        return isDisplayed(searchedProductsTitle);
    }

    public void addFirstProductToCart() {
        click(firstAddToCart);
    }

    public void clickSecondViewProduct() {
        click(secondViewProduct);
    }

    public boolean areSearchResultsDisplayed() {
        return !driver.findElements(searchResults).isEmpty();
    }

    public void clickContinueShopping() {
        click(continueShoppingButton);
    }

    public ProductDetailsPage openSecondProduct() {
        clickSecondViewProduct();
        return new ProductDetailsPage(driver);
    }
}
