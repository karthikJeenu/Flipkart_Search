package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.logging.Level;

// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases {
    ChromeDriver driver;

    @Test
    public void testCase01() throws InterruptedException {
        int count = 0;
         driver.get("https://www.flipkart.com/");
        By search_box = By.xpath("//input[@class='Pke_EE']");
        Wrappers.Search_text(driver,search_box, "Washing Machine");
        WebElement populatity_filter = driver.findElement(By.xpath("//div[text()='Popularity']"));
         populatity_filter.click();
         Thread.sleep(3000);
         List<WebElement> ratings=driver.findElements(By.xpath("//span[contains(@id,'productRating')]//div[text()<4.2]"));
        count=ratings.size();
        System.out.println("The Count of items with rating less than or equal to 4 stars are: " + count);
    }

    @Test
    public void testCase02() throws InterruptedException {
        By search_box = By.className("zDPmFV");
        Wrappers.Search_text(driver,search_box, "iPhone");
       Thread.sleep(2000);
    WebElement locator=driver.findElement(By.xpath("//a[@class='CGtC98']"));
    Wrappers.discountedIphones(locator);
    }


    @Test
    public void testCase03() throws InterruptedException {
        By search_box = By.className("zDPmFV");
        Wrappers.Search_text(driver,search_box, "Coffee Mug");
       Thread.sleep(2000);
    WebElement Four_star_above=driver.findElement(By.xpath("//div[contains(text(),'4') and contains(text(),'above')]"));
    Wrappers.ClickonElement(driver,Four_star_above);
    Thread.sleep(2000);
    By locator=By.className("Wphh3N");
    Wrappers.Top_Coffemugs(driver,locator);
    }

    @BeforeTest
    public void startBrowser() {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
    }

    @AfterTest
    public void endTest() {
        driver.close();
        driver.quit();

    }
}