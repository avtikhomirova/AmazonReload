import TestComponents.BaseTest;
import config.ConfigLoader;
import generators.RandomData;
import org.testng.annotations.Test;
import pages.*;

public class AmazonReloadTest extends BaseTest {
    @Test
    public void giftCardPurchase() throws InterruptedException {
        String email = ConfigLoader.getConfig().getProperty("email");
        String cardNumber = ConfigLoader.getConfig().getProperty("cardNumber");
        String password = ConfigLoader.getConfig().getProperty("password");
        int purchaseQuantity = Integer.parseInt(ConfigLoader.getConfig().getProperty("purchaseQuantity"));
        driver = getDriver();


        for (int i = 1; i <= purchaseQuantity; i++) {
            String purchaseAmount = RandomData.getPrice();
            AmazonReloadPage amazonReloadPage = launchApplication();
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

            //Check if the order placement complete successfully
            OrderResultPage orderResultPage = new OrderResultPage(driver);
            orderResultPage.checkOrderStatus();
        }

    }
}
