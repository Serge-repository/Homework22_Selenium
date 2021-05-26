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

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.testng.Assert.assertTrue;

public class H22Task2_2 extends Task2 {
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
    public void searchByPrice() {

        samsungPhonesSearch(driver, wait);

        scrollMethod(driver.findElement(By.cssSelector("label[for=Samsung]")), driver);

        By byMinPrice = By.cssSelector("input[formcontrolname=min]");
        By byMaxPrice = By.cssSelector("input[formcontrolname=max]");
        By bySubmit = By.xpath("(//button[@type='submit'])[1]");

        driver.findElement(byMinPrice).clear();
        driver.findElement(byMinPrice).sendKeys("5000");
        driver.findElement(byMaxPrice).clear();
        driver.findElement(byMaxPrice).sendKeys("15000");

        driver.findElement(bySubmit).click();
        wait.until(elementToBeClickable(bySubmit));

        List<WebElement> elementsList = driver.findElements(By.cssSelector("span.goods-tile__price-value"));
        for (WebElement elem : elementsList) {
            int goodPrice = Integer.parseInt(elem.getText().replace("&nbsp", "").replaceAll(" ", ""));
            assertTrue(goodPrice > 5000 || goodPrice < 15000);
        }
    }
}
