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

public class CheckoutTest extends BaseTest {

    @Test
    public void completeOrderAndDownloadInvoiceTest() {

        HomePage homePage = new HomePage(driver);

        // 1. Verify home page
        Assert.assertTrue(
                homePage.isHomePageDisplayed(),
                "Home page is not displayed"
        );

        // 2. Go to Products
        ProductsPage productsPage = homePage.clickProducts();

        Assert.assertTrue(
                productsPage.isAllProductsDisplayed(),
                "ALL PRODUCTS page is not displayed"
        );

        // 3. Search product
        productsPage.searchProduct(TestData.PRODUCT_NAME);

        Assert.assertTrue(
                productsPage.isSearchedProductsDisplayed(),
                "SEARCHED PRODUCTS is not displayed"
        );

        // 4. Add first product
        productsPage.addFirstProductToCart();

        // 5. Continue Shopping
        productsPage.clickContinueShopping();

        // 6. Open second product
        ProductDetailsPage productDetailsPage =
                productsPage.openSecondProduct();

        // 7. Verify details
        Assert.assertTrue(
                productDetailsPage.isProductDetailsDisplayed(),
                "Product details are not displayed"
        );

        // 8. Set quantity to 4
        productDetailsPage.setQuantity(4);

        // 9. Add second product
        CartPage cartPage =
                productDetailsPage.addProductToCartWithQuantity(4);

        // 10. View Cart
        cartPage.clickViewCart();

        // 11. Verify products
        Assert.assertTrue(
                cartPage.areProductsDisplayed(),
                "Products are not displayed in cart"
        );

        // 12. Verify second product quantity
        Assert.assertEquals(
                cartPage.getSecondProductQuantity(),
                4,
                "Second product quantity should be 4"
        );

        // 13. Verify second product total
        Assert.assertTrue(
                cartPage.isSecondProductTotalCorrect(),
                "Second product total is incorrect"
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
    public byte[] attachScreenshot(String name) {

        return ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.BYTES);
    }
}