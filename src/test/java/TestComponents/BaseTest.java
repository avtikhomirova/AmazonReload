package TestComponents;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import pages.AmazonReloadPage;
import resources.ConfigLoader;

public class BaseTest {
    public static WebDriver driver;

    public static WebDriver getDriver() {
        ConfigLoader configLoader = new ConfigLoader();
        String browserName = configLoader.getProperty("browser");

        //Set user-agent
        if (browserName.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments(
                    "user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36");
            options.addArguments("--headless=new");
            driver = new FirefoxDriver(options);
        } else if (browserName.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            System.setProperty("webdriver.chrome.driver", "/Users/aleksandrapopova/chromedriver/mac_arm-118.0.5993.70/chromedriver-mac-arm64/chromedriver");
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
        driver.close();
    }


}
