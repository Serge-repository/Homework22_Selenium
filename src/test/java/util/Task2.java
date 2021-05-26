package util;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class Task2 {

    public static void scrollMethod(WebElement element, WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void samsungPhonesSearch(WebDriver driver, WebDriverWait wait) {
        driver.findElement(By.name("search")).sendKeys("samsung" + Keys.ENTER);
        wait.until(presenceOfElementLocated(By.xpath("//*[contains(@href, 'mobile-phones')]")));
        driver.findElement(By.xpath("//*[contains(@href, 'mobile-phones')]")).click();
    }
}
