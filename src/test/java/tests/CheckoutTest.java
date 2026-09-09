package tests;

import base.BaseTest;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.FileUtils;
import utils.TestData;

import java.io.File;
import java.math.BigDecimal;

public class CheckoutTest extends BaseTest {

    @Test
    public void completeOrderAndDownloadInvoiceTest() {

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
                        TestData.VALID_EMAIL,
                        TestData.PASSWORD
                );

        // 5. Verify logged in successfully
        Assert.assertTrue(
                accountPage.isLoggedIn(TestData.NAME),
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
        productsPage.searchProduct(TestData.PRODUCT_NAME);

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

        // 11. Increase quantity to 4
        productDetailsPage.setQuantity(4);

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
                4,
                "Second product quantity should be 4"
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
        // 14. Proceed to checkout
        CheckoutPage checkoutPage =
                cartPage.proceedToCheckout();

        // 15. Verify Address Details
        Assert.assertTrue(
                checkoutPage.isAddressDetailsDisplayed(),
                "Address Details is not displayed"
        );

        // 16. Verify Review Your Order
        Assert.assertTrue(
                checkoutPage.isReviewOrderDisplayed(),
                "Review Your Order is not displayed"
        );

        // 17. Enter comment
        checkoutPage.enterComment(
                "Please deliver the order as soon as possible."
        );

        // 18. Place Order
        PaymentPage paymentPage =
                checkoutPage.clickPlaceOrder();

        // 19. Enter payment details
        paymentPage.enterPaymentDetails(
                TestData.CARD_NAME,
                TestData.CARD_NUMBER,
                TestData.CVC,
                TestData.EXPIRATION_MONTH,
                TestData.EXPIRATION_YEAR
        );

        // 20. Pay and Confirm Order
        OrderConfirmationPage confirmationPage =
                paymentPage.payAndConfirmOrder();

        // 21. Verify success message
        Assert.assertTrue(
                confirmationPage.isOrderPlacedSuccessfully(),
                "Your order has been placed successfully! message is not displayed"
        );

        // 22. Download invoice
        confirmationPage.downloadInvoice();

        // 23. Verify invoice downloaded
        File invoice = FileUtils.waitForInvoiceDownload(
                "invoice",
                10
        );

        Assert.assertNotNull(
                invoice,
                "Invoice was not downloaded"
        );

        Assert.assertTrue(
                invoice.exists(),
                "Downloaded invoice file does not exist"
        );

        // 24. Attach screenshot to Allure
        attachScreenshot("Order Confirmation / Invoice");
    }


    @Attachment(
            value = "{name}",
            type = "image/png"
    )
    public byte[] attachScreenshot(String   name) {

        return ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.BYTES);
    }
}