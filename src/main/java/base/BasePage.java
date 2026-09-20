package base;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    private static final Duration DEFAULT_TIMEOUT =
            Duration.ofSeconds(10);


    private final By googleAdFrames =
            By.cssSelector(
                    "iframe[id^='aswift_'], iframe[id^='google_ads_iframe_']"
            );

    private final By googleAdCloseButton =
            By.xpath(
                    "//div[contains(@class,'continue-prompt-text') " +
                            "and normalize-space()='Close']"
            );

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
    }

    protected void click(By locator) {

        closeGoogleAdIfPresent();

        WebElement element = wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        );

        try {

            element.click();

        } catch (ElementClickInterceptedException e) {

            // The ad may have appeared after our first check.
            closeGoogleAdIfPresent();

            element = wait.until(
                    ExpectedConditions.elementToBeClickable(locator)
            );

            try {

                element.click();

            } catch (ElementClickInterceptedException secondException) {

                // Last fallback for an element still blocked by an overlay.
                ((JavascriptExecutor) driver)
                        .executeScript(
                                "arguments[0].scrollIntoView({block: 'center'});",
                                element
                        );

                ((JavascriptExecutor) driver)
                        .executeScript(
                                "arguments[0].click();",
                                element
                        );
            }
        }
    }

    protected void type(By locator, String text) {

        closeGoogleAdIfPresent();

        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );

        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {

        closeGoogleAdIfPresent();

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        ).getText();
    }

    protected boolean isDisplayed(By locator) {

        closeGoogleAdIfPresent();

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        ).isDisplayed();
    }

    protected void closeGoogleAdIfPresent() {

        try {

            driver.switchTo().defaultContent();

            List<WebElement> frames =
                    driver.findElements(googleAdFrames);

            for (WebElement frame : frames) {

                if (!frame.isDisplayed()) {
                    continue;
                }

                try {

                    driver.switchTo().frame(frame);

                    List<WebElement> closeButtons =
                            driver.findElements(googleAdCloseButton);

                    if (!closeButtons.isEmpty()) {

                        for (WebElement closeButton : closeButtons) {

                            if (closeButton.isDisplayed()) {

                                try {
                                    closeButton.click();
                                } catch (Exception e) {
                                    ((JavascriptExecutor) driver)
                                            .executeScript(
                                                    "arguments[0].click();",
                                                    closeButton
                                            );
                                }

                                driver.switchTo().defaultContent();
                                return;
                            }
                        }
                    }

                } catch (NoSuchElementException ignored) {

                    // This frame does not contain the ad close button.

                } finally {

                    driver.switchTo().defaultContent();
                }
            }

        } catch (Exception ignored) {

            // Ad handling must never break the actual test.

            try {
                driver.switchTo().defaultContent();
            } catch (Exception ignoredAgain) {
                // Ignore
            }
        }
    }
}