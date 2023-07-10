package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AmazonReloadPage {
    private WebDriver driver;

    @FindBy(css = "input[name=oneTimeReloadAmount]")
    private WebElement oneTimeReloadAmount;

    @FindBy(css = "div[data-csa-c-content-id=buyNow]")
    private WebElement buyNowBtn;

    public void oneTimeReloadPurchase(String amount){
        oneTimeReloadAmount.click();
        oneTimeReloadAmount.sendKeys(amount);
        buyNowBtn.click();
    }

    public AmazonReloadPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
