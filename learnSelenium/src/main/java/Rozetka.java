import dev.failsafe.internal.util.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.concurrent.TimeUnit;

public class Rozetka {
    public static void main(String[] args) throws InterruptedException {

        final String ROZETKAURL = "https://rozetka.com.ua/ua/spell_4820207310490/p42832496/";
        String expectedCount = "5";
        int defPrice;
        int actualPrice;
        int expectedPrice;

        System.setProperty("webdriver.chrome.driver", "K:\\soft\\tools\\driver\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");

        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.get(ROZETKAURL);
        driver.manage().timeouts().pageLoadTimeout(5,TimeUnit.SECONDS);
        Thread.sleep(1000);

        //знаходимо кнопку "купити" й натискаємо
        driver.findElement(By.xpath("//button[@aria-label='Купити']")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        //отримати сумму за 1 штуку
        WebElement priceEach = driver.findElement(By.xpath("//*[@id=\"#scrollArea\"]/div[1]/div[2]/div/rz-product-main-info/div/div[2]/div[1]/div[1]/p[2]"));
        defPrice = Integer.parseInt(priceEach.getText().replace("₴",""));
        System.out.println("defPrice=" + defPrice);

        //віконце кількості товару
        WebElement counter = driver.findElement(By.xpath("//input[@formcontrolname='quantity']"));
        counter.click();
        counter.clear();
        counter.click();
        Thread.sleep(1000);
        counter.sendKeys(expectedCount);
        Thread.sleep(1000);

        //натиснути на "оформити замовлення"
        driver.findElement(By.xpath("/html/body/rz-app-root/rz-modal/rz-modal-layout/div[2]/rz-shopping-cart/div/div[1]/div/rz-checkout-button/a/span")).click();

        //перевірити сумму за 5 товарів
        WebElement sum = driver.findElement(By.xpath("//dd[@class='checkout-total__value checkout-total__value_size_large']"));
        actualPrice = Integer.parseInt(sum.getText().replace("₴",""));

        expectedPrice = defPrice * Integer.parseInt(expectedCount);

        System.out.println("actualPrice=" + actualPrice + "\n" +
                "expectedPrice=" + expectedPrice);

        Boolean bol = actualPrice == expectedPrice; // isTrue порівнює стрінги, а у мене інт. тому зробив тут бул

        Assert.isTrue(bol,"SUM ERROR. Expected: " + expectedPrice + " but actual is "+ actualPrice);
        driver.quit();
    }
}