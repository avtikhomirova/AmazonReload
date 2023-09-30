import Pages.AmazonReloadPage;
import Pages.CheckoutPage;
import Pages.OrderResultPage;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.text.DecimalFormat;

import java.time.Duration;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        ConfigLoader configLoader = new ConfigLoader();
        String email = configLoader.getProperty("email");
        String cardNumber = configLoader.getProperty("cardNumber");

        Random rand = new Random();
        float min = 0.50f;
        float max = 0.70f;


        int purchaseQuantity = configLoader.getIntProperty("purchaseQuantity");

        // Launch driver
        System.setProperty("webdriver.chrome.driver", "/Users/aleksandrapopova/Documents/WebDriver/chromedriver");
        WebDriver driver = new ChromeDriver();
//        if (driver instanceof JavascriptExecutor) {
//            ((JavascriptExecutor) driver).executeScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");
//        }

        for (int i = 1; i <= purchaseQuantity; i++) {
            //Get random purchase amount
            float randomFloat = min + rand.nextFloat() * (max - min);
            DecimalFormat df = new DecimalFormat("0.00");
            String purchaseAmount = df.format(randomFloat);

            // Open Amazon Reload
            driver.get("https://www.amazon.com/gp/gc/create");
            //driver.navigate().refresh();

            // Set implicit wait
            //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

            //Set explicit wait
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

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
                wait.until(ExpectedConditions.elementToBeSelected(By.cssSelector("input[name=rememberMe]")));
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
            System.out.println(driver.getCurrentUrl());
            wait.until(ExpectedConditions.urlContains("https://www.amazon.com/gp/buy/"));
            OrderResultPage orderResultPage = new OrderResultPage(driver);
            orderResultPage.checkOrderStatus();

        }
        driver.close();
    }
}
