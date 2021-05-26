package ui_tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import util.Task2;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.testng.Assert.assertTrue;

public class H22Task2_1 extends Task2 {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void actionsBeforeTestClass() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @AfterClass
    public void actionsAfterTestClass() {
        driver.quit();
    }

    @BeforeMethod
    public void actionsBeforeTestMethod() {
        driver.get("https://rozetka.com.ua/");
    }

    @Test
    public void searchByManufacturers() {

        samsungPhonesSearch(driver, wait);

        By bySeller = By.cssSelector("label[for=Rozetka]");
        By byLabelApple = By.cssSelector("label[for=Apple]");
        By byLabelHuawei = By.cssSelector("label[for=Huawei]");

        wait.until(presenceOfElementLocated(bySeller));
        scrollMethod(driver.findElement(bySeller), driver);

        wait.until(presenceOfElementLocated(byLabelApple));
        driver.findElement(byLabelApple).click();
        driver.findElement(byLabelHuawei).click();
        wait.until(presenceOfElementLocated(byLabelHuawei));

        List<WebElement> elementsList = driver.findElements(By.cssSelector("span.goods-tile__title"));
        for (WebElement e : elementsList) {
            assertTrue(e.getText().contains("Samsung") || e.getText().contains("Apple") || e.getText().contains("Huawei"));
        }
    }
}
