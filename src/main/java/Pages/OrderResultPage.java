package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class OrderResultPage {
    private WebDriver driver;

    @FindBy(className = "a-alert-heading")
    private WebElement orderStatus;

    public void checkOrderStatus(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(orderStatus));
        String orderStatusText = orderStatus.getText();
        if (orderStatusText.equals("Order placed, thanks!")){
            System.out.println("Order placed successfully");
        } else {
            System.out.println("Smth goes wrong");
            System.exit(1);
        }
    }

    public OrderResultPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


}
