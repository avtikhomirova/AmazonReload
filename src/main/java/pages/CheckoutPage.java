package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CheckoutPage extends Page {
    private WebDriver driver;

    @FindBy(id = "payChangeButtonId")
    private WebElement payChangeButton;

    @FindBy(className = "pmts-credit-card-row")
    private List<WebElement> cardParentElements;

    @FindBy(className = "pmts-cc-number")
    private WebElement cardNumberElement;

    @FindBy(id = "orderSummaryPrimaryActionBtn")
    private WebElement useThisPaymentMethodBtn;

    @FindBy(id = "payment-option-text-default")
    private WebElement payWithString;

    @FindBy(xpath = "//h2[@id='payment-option-text-default']/span")
    private WebElement cardEndingNumber;

    @FindBy(className = "grand-total-price")
    private WebElement priceElement;

    @FindBy(css = "#submitOrderButtonId")
    private WebElement placeYourOrderAndPayBtn;

    @FindBy(className = "place-order-button-text")
    private WebElement placeYourOrderAndPayBtnText;


    // Constructor
    public CheckoutPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void changeCardForPayment(String cardNumberToFind) throws InterruptedException {
        Thread.sleep(5000);
        waitForElementPresence(By.id("payChangeButtonId"));
        clickElement(payChangeButton);
        waitForElementPresence(By.xpath("//span[contains(@class, 'a-size-medium') and contains(@class, 'a-text-bold') and text()='Your credit and debit cards']"));
        waitForAllElementsPresence(By.className("pmts-credit-card-row"));

        for (WebElement cardParentElement : cardParentElements) {
            WebElement cardNumberElement = cardParentElement.findElement(By.className("pmts-cc-number"));
            String cardNumber = cardNumberElement.getAttribute("data-number");

            if (cardNumberToFind.equals(cardNumber)) {
                selectCard(cardParentElement);
                clickElement(useThisPaymentMethodBtn);
                break;
            }
        }
        waitForElementToAppear(By.id("payment-option-text-default"));
        waitForElementToBeClickable(By.id("bottomSubmitOrderButtonId"));
    }

    private void selectCard(WebElement cardParentElement) {
        WebElement radioButton = cardParentElement.findElement(By.xpath(".//input[@type='radio']"));
        waitForElementPresence(By.xpath(".//input[@type='radio']"));
        clickElement(radioButton);
    }

    public void cardNumberCheck(String cardNumberToFind) {
        waitTextToBe(cardEndingNumber, cardNumberToFind);
        assertTextContains(cardEndingNumber, cardNumberToFind);
    }

    private void assertTextContains(WebElement element, String expectedText) {
        String actualText = element.getText();
        if (!actualText.contains(expectedText)) {
            throw new AssertionError("Expected text not found: " + expectedText);
        }
    }

    public void purchaseAmountCheck(String amount) {
        String expectedText = "$" + amount;
        assertTextEquals(priceElement, expectedText);
    }

    private void assertTextEquals(WebElement element, String expectedText) {
        String actualText = element.getText();
        if (!actualText.equals(expectedText)) {
            throw new AssertionError("Expected: " + expectedText + ", Found: " + actualText);
        }
    }

    public void paymentFinish() throws InterruptedException {
        Thread.sleep(5000);
        waitTextToBe(placeYourOrderAndPayBtnText, "Place your order");
        waitForElementToBeClickable(By.cssSelector("#submitOrderButtonId"));
        if (placeYourOrderAndPayBtn.isDisplayed()) {
            clickElement(placeYourOrderAndPayBtn);
        } else {
            throw new IllegalStateException("Payment button is not displayed");
        }
    }

    private void clickElement(WebElement element) {
        element.click();
    }
    //try {
//                //Wait for an element on the page to become visible or clickable
//                WebDriverWait wait = new WebDriverWait(driver, 10);
//                WebElement cvvInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("addCreditCardVerificationNumber")));
//                System.out.println("You caught a cvc page");
//                cvvInput.sendKeys();
//            } finally {
//                OrderResultPage orderResultPage = new OrderResultPage(driver);
//                orderResultPage.checkOrderStatus();
//            }
}

