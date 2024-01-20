package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AmazonReloadPage extends Page {
    private WebDriver driver;
    private final String AMAZON_RELOAD_PAGE_URL = "https://www.amazon.com/gp/gc/create";
    public AmazonReloadPage(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(name = "oneTimeReloadAmount")
    private WebElement oneTimeReloadAmount;
    By oneTimeReloadAmountBy = By.name("oneTimeReloadAmount");
    @FindBy(xpath = "(//input[@aria-labelledby='gcui-asv-reload-buynow-button-announce'])[1]")
    private WebElement buyNowBtn;

    public WebDriver openAmazonReloadPage(){
        driver.get(AMAZON_RELOAD_PAGE_URL);
        return driver;
    }

    public void oneTimeReloadPurchase(String amount){
        oneTimeReloadAmount.click();
        oneTimeReloadAmount.sendKeys(amount);
        waitForElementToAppear(oneTimeReloadAmountBy);
        buyNowBtn.sendKeys(Keys.ENTER);
    }


}
