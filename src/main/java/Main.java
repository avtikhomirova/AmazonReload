import Pages.AmazonReloadPage;
import Pages.CheckoutPage;
import Pages.OrderResultPage;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Main {
    public static void main(String[] args) {

        ConfigLoader configLoader = new ConfigLoader();
        String email = configLoader.getProperty("email");
        String cardNumber = configLoader.getProperty("cardNumber");
        String purchaseAmount = configLoader.getProperty("purchaseAmount");
        int purchaseQuantity = configLoader.getIntProperty("purchaseQuantity");

        // Launch driver
        WebDriver driver = new ChromeDriver();
        if (driver instanceof JavascriptExecutor) {
            ((JavascriptExecutor) driver).executeScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");
        }

        for (int i = 1; i <= purchaseQuantity; i++) {
            // Open Amazon Reload
            driver.get("https://www.amazon.com/gp/gc/create");
            driver.navigate().refresh();

            // Wait for page loading
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            /* Set focus on the input with purchase amount.
            Insert the purchase amount.
            Click on the button "Buy now".
            */
            AmazonReloadPage amazonReloadPage = new AmazonReloadPage(driver);
            amazonReloadPage.oneTimeReloadPurchase(purchaseAmount);

            // Insert the email
            if (i == 1) {
                WebElement emailInput = driver.findElement(By.id("ap_email"));

                emailInput.sendKeys(email);

                // Click on the button "Continue"
                WebElement continueButton = driver.findElement(By.id("continue"));
                continueButton.click();

                /* Wait for password insert and "Keep me signed" check box activation.
                   To avoid CAPTCHA, you need to enter the password and click sign up button manually.*/
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
                wait.until(ExpectedConditions.elementToBeSelected(By.cssSelector("input[name=rememberMe]")));
            }

            // Change the pay type
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            CheckoutPage checkoutPage = new CheckoutPage(driver);
            checkoutPage.changeCardForPayment(cardNumber);

            // Check if card number is ok
            checkoutPage.cardNumberCheck(cardNumber);

            // Check if the purchase amount is ok
            checkoutPage.purchaseAmountCheck(purchaseAmount);

            //Click Place your order and pay button
            checkoutPage.paymentFinish();

            //Check if the order placement complete successfully
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            OrderResultPage orderResultPage = new OrderResultPage(driver);
            orderResultPage.checkOrderStatus();

        }
        driver.close();
    }
}
