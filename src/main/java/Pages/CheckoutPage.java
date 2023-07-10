package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

    @FindBy(css = "span[id=submitOrderButtonId-announce]")
    private WebElement placeYourOrderAndPayBtn;


    public void changeCardForPayment(String cardNumberToFind){
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(payChangeButton));
        payChangeButton.click();

        for (WebElement cardParentElement : cardParentElements) {
            WebElement cardNumberElement = cardParentElement.findElement(By.className("pmts-cc-number"));
            String cardNumber = cardNumberElement.getAttribute("data-number");

            if (cardNumberToFind.equals(cardNumber)) {
                System.out.println("Found card number: " + cardNumber);
                // Find the associated radio button and click it
                WebElement radioButton = cardParentElement.findElement(By.xpath(".//input[@type='radio']"));
                new WebDriverWait(driver, Duration.ofSeconds(10))
                        .until(ExpectedConditions.elementToBeClickable(radioButton));
                radioButton.click();
                break;
            }
        }
        useThisPaymentMethodBtn.click();//Click button "Use this payment method" to finish payment type change
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(placeYourOrderAndPayBtn));

    }

    public void cardNumberCheck(String cardNumberToFind){
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.textToBe(By.cssSelector("span[data-field=tail]"), cardNumberToFind));

        if (cardEndingNumber.getText().equals(cardNumberToFind)) {
            System.out.println("Chosen card number on the pay page is correct");
        } else {
            System.out.println("Number of the card on the page doesn't equals to chosen card");
            System.exit(1);
        }
    }

    public void purchaseAmountCheck(String amount){
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.textToBe(By.className("grand-total-price"), "$" + amount));

        String priceText = priceElement.getText();
        String expectedText = ("$" + amount);

        if(priceText.equals(expectedText)) {
            System.out.println("The price is correct.");
        } else {
            System.err.println("The price is incorrect.");
            System.exit(1);
        }
    }

    public void paymentFinish (){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        Actions actions = new Actions(driver);
        actions.moveToElement(placeYourOrderAndPayBtn).click().build().perform();
    }


    public CheckoutPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
