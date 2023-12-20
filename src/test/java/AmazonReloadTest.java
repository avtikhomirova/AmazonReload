import generators.RandomData;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.*;

import java.time.Duration;

public class AmazonReloadTest {
    public static void main(String[] args) {

        ConfigLoader configLoader = new ConfigLoader();
        String email = configLoader.getProperty("email");
        String cardNumber = configLoader.getProperty("cardNumber");
        String password = configLoader.getProperty("password");
        int purchaseQuantity = configLoader.getIntProperty("purchaseQuantity");

        //Set user-agent
        //ChromeOptions options = new ChromeOptions();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments(
                "user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36");
        options.addArguments("--headless=new");
        // Launch driver
        WebDriver driver = new FirefoxDriver(options);
        //System.setProperty("webdriver.chrome.driver", "/Users/aleksandrapopova/chromedriver/mac_arm-118.0.5993.70/chromedriver-mac-arm64/chromedriver");
        //WebDriver driver = new ChromeDriver(options);
//        if (driver instanceof JavascriptExecutor) {
//            ((JavascriptExecutor) driver).executeScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");
//        }

        for (int i = 1; i <= purchaseQuantity; i++) {
            String purchaseAmount = RandomData.getPrice();

            // Open Amazon Reload
            driver.get("https://www.amazon.com/gp/gc/create");
            //driver.navigate().refresh();

            //Set explicit wait
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            AmazonReloadPage amazonReloadPage = new AmazonReloadPage(driver);
            amazonReloadPage.oneTimeReloadPurchase(purchaseAmount);

            if (i == 1) {
                SignInEmailPage signInEmailPage = new SignInEmailPage(driver);
                signInEmailPage.fillEmailForSignIn(email);

                SignInPasswordPage signInPasswordPage = new SignInPasswordPage(driver);
                signInPasswordPage.fillPasswordForSignIn(password);
            }

            // Change the pay type
            CheckoutPage checkoutPage = new CheckoutPage(driver);
            checkoutPage.changeCardForPayment(cardNumber);

            // Check if card number is ok
            checkoutPage.cardNumberCheck(cardNumber);

            // Check if the purchase amount is ok
            checkoutPage.purchaseAmountCheck(purchaseAmount);

            //Click Place your order and pay button
            checkoutPage.paymentFinish();

//            try {
//                // Wait for an element on the page to become visible or clickable
//                WebDriverWait wait = new WebDriverWait(driver, 10);
//                WebElement cvvInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("addCreditCardVerificationNumber")));
//                System.out.println("You caught a cvc page");
//                cvvInput.sendKeys();
//            } finally {
//                OrderResultPage orderResultPage = new OrderResultPage(driver);
//                orderResultPage.checkOrderStatus();
//            }

            //Check if the order placement complete successfully
            OrderResultPage orderResultPage = new OrderResultPage(driver);
            orderResultPage.checkOrderStatus();

        }
        driver.close();
    }
}
