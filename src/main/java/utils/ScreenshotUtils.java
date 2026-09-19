package utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;

public final class ScreenshotUtils {

    private ScreenshotUtils() {
        // Prevent instantiation
    }

    public static void attachScreenshot(
            WebDriver driver,
            String testName) {

        if (driver == null) {
            return;
        }

        byte[] screenshot =
                ((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.BYTES);

        Allure.addAttachment(
                testName + " - Screenshot",
                "image/png",
                new ByteArrayInputStream(screenshot),
                ".png"
        );
    }
}