import dev.failsafe.internal.util.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
         final String PB24URL = "https://privatbank.ua/";

        System.setProperty("webdriver.chrome.driver", "K:\\soft\\tools\\driver\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions chromeOptions = new ChromeOptions();
        //String version = "latest";
        chromeOptions.addArguments("--start-maximized");
        //chromeOptions.setBrowserVersion(version);

        WebDriver driver = new ChromeDriver(chromeOptions);

        driver.get("https://google.com");

        //знаходиом рядок пошука та пишемо туди текст
        WebElement searchField = driver.findElement(By.xpath("//textarea[@aria-label='Пошук']"));
        searchField.clear();
        searchField.sendKeys("privatbank.ua", Keys.ENTER);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        // шукаємо 1 рядок, відкриваємо його та перевіряємо УРЛ
        driver.findElement(By.xpath("//h3[@class='LC20lb MBeuO DKV0Md']")).click();
        Assert.isTrue(PB24URL.equalsIgnoreCase(driver.getCurrentUrl()), "UNEXPECTED URL. Acutal Result: " + driver.getCurrentUrl() + " ; But expected: " + PB24URL);

        //шукаємо елемент з курсами валют та натискаємо
        driver.findElement(By.xpath("//button[@class='btn exchange-rate']")).click();

        //отримуємо курси валют та виводимо в консоль
        String eurSell = driver.findElement(By.xpath("//*[@id=\"EUR_sell\"]")).getText();
        String eurBuy = driver.findElement(By.xpath("//*[@id=\"EUR_buy\"]")).getText();

        String usdSell = driver.findElement(By.xpath("//*[@id=\"USD_sell\"]")).getText();
        String usdBuy = driver.findElement(By.xpath("//*[@id=\"USD_buy\"]")).getText();


        String plnSell = driver.findElement(By.xpath("//*[@id=\"PLN_sell\"]")).getText();
        String plnBuy = driver.findElement(By.xpath("//*[@id=\"PLN_buy\"]")).getText();

        System.out.println("EURO купівля: "+ eurBuy + "; EURO продаж: " + eurSell + "\n" +
                "USD купівля: "+ usdBuy + "; USD продаж: " + usdSell + "\n" +
                "PLN купівля: "+ plnBuy + "; PLN продаж: " + plnSell );

        driver.quit();
    }

}

