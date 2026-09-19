package tests;

import base.BaseTest;
import data.TestData;
import data.TestDataLoader;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

import java.math.BigDecimal;

public class ProductTest extends BaseTest {

    private final TestData testData =
            TestDataLoader.getTestData();

    @Test
    public void searchAndAddProductsToCartTest() {

        HomePage homePage = new HomePage(driver);

        // 1. Verify home page
        Assert.assertTrue(
                homePage.isHomePageDisplayed(),
                "Home page is not displayed"
        );

        LoginPage loginPage = homePage.clickSignupLogin();

        // 2. Verify "Login to your account"
        Assert.assertTrue(
                loginPage.isLoginAccountDisplayed(),
                "Login to your account section is not displayed"
        );

        // 3 + 4. Enter valid credentials and click Login
        AccountPage accountPage =
                loginPage.login(
                        testData.getRegistration().getValidEmail(),
                        testData.getRegistration().getPassword()
                );

        // 5. Verify logged in successfully
        Assert.assertTrue(
                accountPage.isLoggedIn(
                        testData.getRegistration().getName()
                ),
                "User is not logged in successfully"
        );

        // Clean existing cart state
        CartPage cartPage = homePage.clickCart();
        cartPage.removeAllProductsFromCart();

        // 2. Navigate to Products
        ProductsPage productsPage = homePage.clickProducts();

        // 3. Verify ALL PRODUCTS page
        Assert.assertTrue(
                productsPage.isAllProductsDisplayed(),
                "ALL PRODUCTS page is not displayed"
        );

        // 4. Search for product
        productsPage.searchProduct(
                testData.getProduct().getName()
        );

        // 5. Verify SEARCHED PRODUCTS
        Assert.assertTrue(
                productsPage.isSearchedProductsDisplayed(),
                "SEARCHED PRODUCTS is not displayed"
        );

        // 6. Verify search results
        Assert.assertTrue(
                productsPage.areSearchResultsDisplayed(),
                "Products related to the search are not displayed"
        );

        // 7. Add first product to cart
        productsPage.addFirstProductToCart();

        // 8. Continue shopping
        productsPage.clickContinueShopping();

        // 9. Open second product
        ProductDetailsPage productDetailsPage =
                productsPage.openSecondProduct();

        // 10. Verify product details
        Assert.assertTrue(
                productDetailsPage.isProductDetailsDisplayed(),
                "Product details page is not displayed"
        );

        // 11. Increase quantity
        productDetailsPage.setQuantity(
                testData.getProduct().getSecondProductQuantity()
        );

        // 12. Add second product to cart
        productDetailsPage.addToCartProductDetailsPage();

        // 13. View Cart
        cartPage = productDetailsPage.clickViewCart();

        // 14. Verify products are in cart
        Assert.assertTrue(
                cartPage.areProductsDisplayed(),
                "Products are not displayed in the cart"
        );

        // 15. Verify first product quantity
        Assert.assertEquals(
                cartPage.getFirstProductQuantity(),
                1,
                "First product quantity is incorrect"
        );

        // 16. Verify second product quantity
        Assert.assertEquals(
                cartPage.getSecondProductQuantity(),
                testData.getProduct().getSecondProductQuantity(),
                "Second product quantity is incorrect"
        );

        // 17. Verify first product total
        BigDecimal firstExpectedTotal =
                cartPage.getFirstProductPrice()
                        .multiply(
                                BigDecimal.valueOf(
                                        cartPage.getFirstProductQuantity()
                                )
                        );

        Assert.assertEquals(
                cartPage.getFirstProductTotal(),
                firstExpectedTotal,
                "First product total is incorrect"
        );

        // 18. Verify second product total
        Assert.assertTrue(
                cartPage.isSecondProductTotalCorrect(),
                "Second product total is not correctly calculated"
        );
    }
}