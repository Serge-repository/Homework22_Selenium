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
import util.Task1;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElementLocated;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class H22Task1 extends Task1 {
    WebDriver driver;
    WebDriverWait wait;
    WebElement element;

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
    public void compareMonitors() {
        int comparedPrice;
        By byGoodGeneralDescription = By.cssSelector("a[class='tabs__link tabs__link--active']");
        By byNumberAddedToCompareList = By.xpath("//rz-icon-counter//child::span");
        By byDirectLink = By.xpath("(//a[contains(@href, 'computers-notebooks/c80253')])[2]");
        By byProductPrices = By.cssSelector("div[class='product__prices']");

        wait.until(presenceOfElementLocated(byDirectLink));
        element = driver.findElement(byDirectLink);

        locateToDropdown(driver, element);

        element = driver.findElement(By.cssSelector("a[href='https://hard.rozetka.com.ua/monitors/c80089/'][class=menu__link]"));
        element.click();

        sleep();

        comparedPrice = 4000;
        findElementByPrice(driver, comparedPrice);
        wait.until(presenceOfElementLocated(byGoodGeneralDescription));
        clickOnCompareButton(driver);
        wait.until(presenceOfElementLocated(byNumberAddedToCompareList));
        assertTrue(driver.findElement(byNumberAddedToCompareList).isDisplayed());

        String monitorTitle = getMonitorTitle(driver);
        int monitorPrice = getMonitorPrice(driver);

        driver.navigate().back();
        wait.until(presenceOfElementLocated(By.cssSelector("h1[class='catalog-heading ng-star-inserted']")));
        comparedPrice = monitorPrice;
        findElementByPrice(driver, comparedPrice);

        wait.until(presenceOfElementLocated(byGoodGeneralDescription));
        clickOnCompareButton(driver);
        wait.until(textToBePresentInElementLocated(byNumberAddedToCompareList, "2"));
        assertEquals(Integer.parseInt(driver.findElement(byNumberAddedToCompareList).getText()), 2);

        String secondMonitorTitle = getMonitorTitle(driver);
        int secondMonitorPrice = getMonitorPrice(driver);

        driver.findElement(By.cssSelector("li[class='header-actions__item header-actions__item--comparison'] button")).click();
        driver.findElement(By.cssSelector("a.comparison-modal__link")).click();

        wait.until(presenceOfElementLocated(byProductPrices));
        List<WebElement> priceElementsList = driver.findElements(byProductPrices);
        assertEquals(priceElementsList.size(), 2);

        List<WebElement> elementsNameList = driver.findElements(By.cssSelector("div.product__heading a"));
        assertEquals(monitorTitle, elementsNameList.get(1).getText());
        assertEquals(secondMonitorTitle, elementsNameList.get(0).getText());

        WebElement elementPriceOne = driver.findElement(By.xpath("(//div[@class='product__price--old']//parent::div)[1]"));
        assertEquals(secondMonitorPrice, Integer.parseInt(elementPriceOne.getText().replaceAll("&nbsp", "")
                .replaceAll(" ", "")
                .replace("₴", "")));

        WebElement elementPriceTwo = driver.findElement(By.xpath("(//div[@class='product__price--old']//parent::div)[3]"));
        assertEquals(monitorPrice, Integer.parseInt(elementPriceTwo.getText().replaceAll("&nbsp", "")
                .replaceAll(" ", "")
                .replace("₴", "")
                .substring(5)));
    }
}