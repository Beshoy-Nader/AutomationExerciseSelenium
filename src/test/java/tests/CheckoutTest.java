package tests;

import base.BaseTest;
import data.TestData;
import data.TestDataLoader;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.FileUtils;

import java.io.File;
import java.math.BigDecimal;

public class CheckoutTest extends BaseTest {

    private final TestData testData =
            TestDataLoader.getTestData();

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

        // 6. Navigate to Products
        ProductsPage productsPage = homePage.clickProducts();

        // 7. Verify ALL PRODUCTS page
        Assert.assertTrue(
                productsPage.isAllProductsDisplayed(),
                "ALL PRODUCTS page is not displayed"
        );

        // 8. Search for product
        productsPage.searchProduct(
                testData.getProduct().getName()
        );

        // 9. Verify SEARCHED PRODUCTS
        Assert.assertTrue(
                productsPage.isSearchedProductsDisplayed(),
                "SEARCHED PRODUCTS is not displayed"
        );

        // 10. Verify search results
        Assert.assertTrue(
                productsPage.areSearchResultsDisplayed(),
                "Products related to the search are not displayed"
        );

        // 11. Add first product to cart
        productsPage.addFirstProductToCart();

        // 12. Continue shopping
        productsPage.clickContinueShopping();

        // 13. Open second product
        ProductDetailsPage productDetailsPage =
                productsPage.openSecondProduct();

        // 14. Verify product details
        Assert.assertTrue(
                productDetailsPage.isProductDetailsDisplayed(),
                "Product details page is not displayed"
        );

        // 15. Increase quantity
        productDetailsPage.setQuantity(
                testData.getProduct().getSecondProductQuantity()
        );

        // 16. Add second product to cart
        productDetailsPage.addToCartProductDetailsPage();

        // 17. View Cart
        cartPage = productDetailsPage.clickViewCart();

        // 18. Verify products are in cart
        Assert.assertTrue(
                cartPage.areProductsDisplayed(),
                "Products are not displayed in the cart"
        );

        // 19. Verify first product quantity
        Assert.assertEquals(
                cartPage.getFirstProductQuantity(),
                1,
                "First product quantity is incorrect"
        );

        // 20. Verify second product quantity
        Assert.assertEquals(
                cartPage.getSecondProductQuantity(),
                testData.getProduct().getSecondProductQuantity(),
                "Second product quantity is incorrect"
        );

        // 21. Verify first product total
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

        // 22. Verify second product total
        Assert.assertTrue(
                cartPage.isSecondProductTotalCorrect(),
                "Second product total is not correctly calculated"
        );

        // 23. Proceed to checkout
        CheckoutPage checkoutPage =
                cartPage.proceedToCheckout();

        // 24. Verify Address Details
        Assert.assertTrue(
                checkoutPage.isAddressDetailsDisplayed(),
                "Address Details is not displayed"
        );

        // 25. Verify Review Your Order
        Assert.assertTrue(
                checkoutPage.isReviewOrderDisplayed(),
                "Review Your Order is not displayed"
        );

        // 26. Enter comment
        checkoutPage.enterComment(
                testData.getCheckout().getComment()
        );

        // 27. Place Order
        PaymentPage paymentPage =
                checkoutPage.clickPlaceOrder();

        // 28. Enter payment details
        paymentPage.enterPaymentDetails(
                testData.getPayment().getCardName(),
                testData.getPayment().getCardNumber(),
                testData.getPayment().getCvc(),
                testData.getPayment().getExpirationMonth(),
                testData.getPayment().getExpirationYear()
        );

        // 29. Pay and Confirm Order
        OrderConfirmationPage confirmationPage =
                paymentPage.payAndConfirmOrder();

        // 30. Verify success message
        Assert.assertTrue(
                confirmationPage.isOrderPlacedSuccessfully(),
                "Your order has been placed successfully! message is not displayed"
        );

        // 31. Download invoice
        confirmationPage.downloadInvoice();

        // 32. Verify invoice downloaded
        File invoice = FileUtils.waitForFileDownload(
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

        // 33. Attach screenshot to Allure
        attachScreenshot("Order Confirmation / Invoice");
    }

    @Attachment(
            value = "{name}",
            type = "image/png"
    )
    public byte[] attachScreenshot(String name) {

        return ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.BYTES);
    }
}