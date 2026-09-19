package tests;

import base.BaseTest;
import data.TestData;
import data.TestDataLoader;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.FileUtils;
import utils.ScreenshotUtils;
import utils.TestDataGenerator;

import java.io.File;
import java.math.BigDecimal;

public class CheckoutTest extends BaseTest {

    private final TestData testData =
            TestDataLoader.getTestData();

    @Test
    public void completeOrderAndDownloadInvoiceTest() {

        // ==========================================
        // 1. Open Home Page
        // ==========================================

        HomePage homePage = new HomePage(driver);

        Assert.assertTrue(
                homePage.isHomePageDisplayed(),
                "Home page is not displayed"
        );

        // ==========================================
        // 2. Register a new user
        // ==========================================

        LoginPage loginPage =
                homePage.clickSignupLogin();

        Assert.assertTrue(
                loginPage.isNewUserSignupDisplayed(),
                "New User Signup section is not displayed"
        );

        String uniqueEmail =
                TestDataGenerator.generateUniqueEmail();

        SignupPage signupPage =
                loginPage.signup(
                        testData.getRegistration().getName(),
                        uniqueEmail
                );

        Assert.assertTrue(
                signupPage.isAccountInformationDisplayed(),
                "ENTER ACCOUNT INFORMATION is not displayed"
        );

        // ==========================================
        // 3. Fill Account Information
        // ==========================================

        signupPage.fillAccountInformation(
                testData.getRegistration().getPassword(),
                testData.getRegistration().getBirthDay(),
                testData.getRegistration().getBirthMonth(),
                testData.getRegistration().getBirthYear()
        );

        signupPage.subscribeToNewsletter();

        signupPage.acceptSpecialOffers();

        // ==========================================
        // 4. Fill Address Information
        // ==========================================

        signupPage.fillAddressInformation(
                testData.getRegistration().getFirstName(),
                testData.getRegistration().getLastName(),
                testData.getRegistration().getCompany(),
                testData.getRegistration().getAddress(),
                testData.getRegistration().getAddress2(),
                testData.getRegistration().getCountry(),
                testData.getRegistration().getState(),
                testData.getRegistration().getCity(),
                testData.getRegistration().getZipcode(),
                testData.getRegistration().getMobile()
        );

        // ==========================================
        // 5. Create Account
        // ==========================================

        AccountPage accountPage =
                signupPage.clickCreateAccount();

        Assert.assertTrue(
                accountPage.isAccountCreated(),
                "ACCOUNT CREATED message is not displayed"
        );

        // ==========================================
        // 6. Continue to logged-in state
        // ==========================================

        accountPage.clickContinue();

        Assert.assertTrue(
                accountPage.isLoggedIn(
                        testData.getRegistration().getName()
                ),
                "User is not logged in successfully"
        );

        // ==========================================
        // 7. Clean Existing Cart
        // ==========================================

        CartPage cartPage =
                homePage.clickCart();

        cartPage.removeAllProductsFromCart();

        // ==========================================
        // 8. Navigate to Products
        // ==========================================

        ProductsPage productsPage =
                homePage.clickProducts();

        Assert.assertTrue(
                productsPage.isAllProductsDisplayed(),
                "ALL PRODUCTS page is not displayed"
        );

        // ==========================================
        // 9. Search for Product
        // ==========================================

        productsPage.searchProduct(
                testData.getProduct().getName()
        );

        Assert.assertTrue(
                productsPage.isSearchedProductsDisplayed(),
                "SEARCHED PRODUCTS is not displayed"
        );

        Assert.assertTrue(
                productsPage.areSearchResultsDisplayed(),
                "Products related to the search are not displayed"
        );

        // ==========================================
        // 10. Add First Product
        // ==========================================

        productsPage.addFirstProductToCart();

        productsPage.clickContinueShopping();

        // ==========================================
        // 11. Add Second Product
        // ==========================================

        ProductDetailsPage productDetailsPage =
                productsPage.openSecondProduct();

        Assert.assertTrue(
                productDetailsPage.isProductDetailsDisplayed(),
                "Product details page is not displayed"
        );

        productDetailsPage.setQuantity(
                testData.getProduct().getSecondProductQuantity()
        );

        productDetailsPage.addToCartProductDetailsPage();

        // ==========================================
        // 12. View Cart
        // ==========================================

        cartPage =
                productDetailsPage.clickViewCart();

        Assert.assertTrue(
                cartPage.areProductsDisplayed(),
                "Products are not displayed in the cart"
        );

        // ==========================================
        // 13. Verify First Product Quantity
        // ==========================================

        Assert.assertEquals(
                cartPage.getFirstProductQuantity(),
                1,
                "First product quantity is incorrect"
        );

        // ==========================================
        // 14. Verify Second Product Quantity
        // ==========================================

        Assert.assertEquals(
                cartPage.getSecondProductQuantity(),
                testData.getProduct().getSecondProductQuantity(),
                "Second product quantity is incorrect"
        );

        // ==========================================
        // 15. Verify First Product Total
        // ==========================================

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

        // ==========================================
        // 16. Verify Second Product Total
        // ==========================================

        Assert.assertTrue(
                cartPage.isSecondProductTotalCorrect(),
                "Second product total is not correctly calculated"
        );

        // ==========================================
        // 17. Proceed to Checkout
        // ==========================================

        CheckoutPage checkoutPage =
                cartPage.proceedToCheckout();

        Assert.assertTrue(
                checkoutPage.isAddressDetailsDisplayed(),
                "Address Details is not displayed"
        );

        Assert.assertTrue(
                checkoutPage.isReviewOrderDisplayed(),
                "Review Your Order is not displayed"
        );

        // ==========================================
        // 18. Enter Order Comment
        // ==========================================

        checkoutPage.enterComment(
                testData.getCheckout().getComment()
        );

        // ==========================================
        // 19. Place Order
        // ==========================================

        PaymentPage paymentPage =
                checkoutPage.clickPlaceOrder();

        // ==========================================
        // 20. Enter Payment Details
        // ==========================================

        paymentPage.enterPaymentDetails(
                testData.getPayment().getCardName(),
                testData.getPayment().getCardNumber(),
                testData.getPayment().getCvc(),
                testData.getPayment().getExpirationMonth(),
                testData.getPayment().getExpirationYear()
        );

        // ==========================================
        // 21. Pay and Confirm Order
        // ==========================================

        OrderConfirmationPage confirmationPage =
                paymentPage.payAndConfirmOrder();

        Assert.assertTrue(
                confirmationPage.isOrderPlacedSuccessfully(),
                "Your order has been placed successfully! message is not displayed"
        );

        // ==========================================
// 22. Download Invoice
// ==========================================

        confirmationPage.downloadInvoice();

        File invoice =
                FileUtils.waitForFileDownload(
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

// ==========================================
// 23. Open Invoice
// ==========================================

        driver.get(invoice.toURI().toString());

// Wait for the PDF to load
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(
                    "Interrupted while waiting for invoice to load.",
                    e
            );
        }

// ==========================================
// 24. Attach Invoice Screenshot
// ==========================================

        ScreenshotUtils.attachScreenshot(
                driver,
                "Downloaded Invoice"
        );
    }
}