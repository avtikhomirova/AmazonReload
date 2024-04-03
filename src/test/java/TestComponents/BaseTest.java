package TestComponents;

import config.ConfigLoader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import pages.AmazonReloadPage;

public class BaseTest {
    public static WebDriver driver;

    public static WebDriver getDriver() {
        String browserName = ConfigLoader.getConfig().getProperty("browser");

        //Set user-agent
        if (browserName.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments(
                    "user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36");
            options.addArguments("--headless=new");
            driver = new FirefoxDriver(options);
        } else if (browserName.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(options);
        }
        return driver;
    }
    public static AmazonReloadPage launchApplication() {
        AmazonReloadPage amazonReloadPage = new AmazonReloadPage(driver);
        amazonReloadPage.openAmazonReloadPage();
        return amazonReloadPage;
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }


}
