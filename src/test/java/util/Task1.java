package util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.stream.IntStream;

public class Task1 {
    public static void locateToDropdown(WebDriver driver, WebElement element) {
        Actions act = new Actions(driver);
        act.moveToElement(element).perform();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void findElementByPrice(WebDriver driver, int comparedPrice) {
        List<WebElement> elementsList = driver.findElements(By.cssSelector("span.goods-tile__price-value"));
        int counter = 1;
        for (WebElement e : elementsList) {
            int x = Integer.parseInt(e.getText().replace("&nbsp", "").replaceAll(" ", ""));
            if (x < comparedPrice) {
                driver.findElement(By.xpath("(//span[@class='goods-tile__price-value']//preceding::a[2])" + "[" + counter + "]")).click();
                break;
            }
            counter++;
        }
//        IntStream.range(0, elementsList.size())
//                .filter(i -> Integer.parseInt(elementsList.get(i).getText().replace(" ", "")) < comparedPrice)
//                .findFirst()
//                .ifPresent(i -> driver.findElement(By.xpath("(//span[@class='goods-tile__price-value']//preceding::a[2])"+  "[" + i+1 +"]")).click());
    }

    public static void clickOnCompareButton(WebDriver driver) {
        driver.findElement(By.xpath("//button[@class='compare-button ng-star-inserted']")).click();
    }

    public static String getMonitorTitle(WebDriver driver) {
        return driver.findElement(By.cssSelector("h1[class='product__title']")).getText();
    }

    public static int getMonitorPrice(WebDriver driver) {
        return Integer.parseInt(driver.findElement(By.cssSelector("p.product-prices__big")).getText()
                .replace("&nbsp", "")
                .replaceAll(" ", "")
                .replace("₴", ""));
    }
}
