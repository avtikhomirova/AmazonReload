package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CheckoutPage {
    private WebDriver driver;

    @FindBy(id = "payChangeButtonId")
    private WebElement payChangeButton;

    @FindBy(className = "pmts-credit-card-row")
    private List<WebElement> cardParentElements;

    @FindBy(className = "pmts-cc-number")
    private WebElement cardNumberElement;

    @FindBy(id = "orderSummaryPrimaryActionBtn")
    private WebElement useThisPaymentMethodBtn;

    @FindBy(css = "span[data-field=tail]")
    private WebElement cardEndingNumber;

    @FindBy(className = "grand-total-price")
    private WebElement priceElement;

    @FindBy(css = "#submitOrderButtonId")
    private WebElement placeYourOrderAndPayBtn;

//    @FindBy(xpath = "//*[@id=\"submitOrderButtonId-announce\"]/span")
//    private WebElement placeYourOrderText;


    public void changeCardForPayment(String cardNumberToFind) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(payChangeButton));
        payChangeButton.click();

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("pmts-credit-card-row")));

        for (WebElement cardParentElement : cardParentElements) {
            WebElement cardNumberElement = cardParentElement.findElement(By.className("pmts-cc-number"));
            String cardNumber = cardNumberElement.getAttribute("data-number");

            if (cardNumberToFind.equals(cardNumber)) {
                System.out.println("Found card number: " + cardNumber);
                // Find the associated radio button and click it
                WebElement radioButton = cardParentElement.findElement(By.xpath(".//input[@type='radio']"));
                wait.until(ExpectedConditions.elementToBeClickable(radioButton));
                radioButton.click();
                useThisPaymentMethodBtn.click();//Click button "Use this payment method" to finish payment type change
                break;
            }
        }

        wait.until(ExpectedConditions.elementToBeClickable(placeYourOrderAndPayBtn));

    }

    public void cardNumberCheck(String cardNumberToFind) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBe(By.cssSelector("span[data-field=tail]"), cardNumberToFind));

        if (cardEndingNumber.getText().equals(cardNumberToFind)) {
            System.out.println("Chosen card number on the pay page is correct");
        } else {
            System.out.println("Number of the card on the page doesn't equals to chosen card");
            System.exit(1);
        }
    }

    public void purchaseAmountCheck(String amount) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBe(By.className("grand-total-price"), "$" + amount));

        String priceText = priceElement.getText();
        String expectedText = ("$" + amount);

        if (priceText.equals(expectedText)) {
            System.out.println("The price is correct.");
        } else {
            System.err.println("The price is incorrect.");
            System.exit(1);
        }

        //driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
    }

//    public void paymentFinish (){
//        new WebDriverWait(driver, Duration.ofSeconds(5))
//               .until(ExpectedConditions.elementToBeClickable(placeYourOrderAndPayBtn));
//        System.out.println("placeYourOrderAndPayBtn is clickable");
//        new WebDriverWait(driver, Duration.ofSeconds(5))
//                .until(ExpectedConditions.textToBePresentInElement(placeYourOrderText,"Place your order"));
//        System.out.println("placeYourOrderAndPayBtn has correct text");
//        Actions actions = new Actions(driver);
//        actions.moveToElement(placeYourOrderAndPayBtn).click().build().perform();
//}
//
    public void paymentFinish() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(placeYourOrderAndPayBtn));
//        for (int i=1; i<=2;i++) {
            if (placeYourOrderAndPayBtn.isDisplayed()) {
                System.out.println("before the click");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                placeYourOrderAndPayBtn.click();
//                Actions actions = new Actions(driver);
//                actions.moveToElement(placeYourOrderAndPayBtn).click().build().perform();
            } else {
                System.exit(1);
            }
        }
//}





    public CheckoutPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
