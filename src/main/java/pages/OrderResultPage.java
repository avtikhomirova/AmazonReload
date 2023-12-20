package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class OrderResultPage extends Page{
    private WebDriver driver;
    private final String ORDER_RESULT_PAGE_URL = "https://www.amazon.com/gp/buy/";
    public OrderResultPage(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    @FindBy(className = "a-alert-heading")
    private WebElement orderStatus;
    By orderStatusBy = By.className("a-alert-heading");

    public void checkOrderStatus(){
        waitForExactUrl(ORDER_RESULT_PAGE_URL);
        waitForElementToAppear(orderStatusBy);
        String orderStatusText = orderStatus.getText();
        if (orderStatusText.equals("Order placed, thanks!")){
            System.out.println("Order placed successfully");
        } else {
            System.out.println("Smth goes wrong");
            System.exit(1);
        }
    }




}
