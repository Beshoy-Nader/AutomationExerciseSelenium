package base;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ScreenshotUtils;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.createDriver();

        driver.get("http://automationexercise.com");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {

        ScreenshotUtils.attachScreenshot(
                driver,
                result.getMethod().getMethodName()
        );

        if (driver != null) {
            driver.quit();
        }
    }
}