import Pages.AmazonReloadPage;
import Pages.CheckoutPage;
import Pages.OrderResultPage;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
        String password = configLoader.getProperty("password");

        Random rand = new Random();
        float min = 0.50f;
        float max = 0.70f;


        int purchaseQuantity = configLoader.getIntProperty("purchaseQuantity");

        //Set user-agent
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36");

        // Launch driver
        System.setProperty("webdriver.chrome.driver", "/Users/aleksandrapopova/chromedriver/mac_arm-118.0.5993.70/chromedriver-mac-arm64/chromedriver");
        WebDriver driver = new ChromeDriver(options);
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

                //Waiting for the password page
                wait.until(ExpectedConditions.urlContains("signin"));

                //Insert the password
                WebElement passwordInput = driver.findElement(By.xpath("//input[@id=\"ap_password\"]"));
                passwordInput.sendKeys(password);

                //Click on checkbox "Keep me signed in"
                WebElement rememberMeCheckbox = driver.findElement(By.name("rememberMe"));
                rememberMeCheckbox.click();

                /* Wait for password insert and "Keep me signed" check box activation.
                   To avoid CAPTCHA, you need to enter the password and click sign up button manually.*/
                wait.until(ExpectedConditions.elementToBeSelected(By.cssSelector("input[name=rememberMe]")));

                //Button "Sign in" button
                WebElement signInButton = driver.findElement(By.id("signInSubmit"));
                signInButton.click();
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
