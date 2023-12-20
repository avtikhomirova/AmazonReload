package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignInEmailPage extends Page {
    WebDriver driver;
    private final String SIGH_IN_PAGE_URL = "www.amazon.com/ap/signin";

    public SignInEmailPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "ap_email")
    private WebElement emailInput;
    By emailInputBy = By.id("ap_email");

    @FindBy(id = "continue")
    private WebElement continueButton;

    public void fillEmailForSignIn(String email) {
        waitForExactUrl(SIGH_IN_PAGE_URL);
        waitForElementPresence(emailInputBy);
        emailInput.sendKeys(email);
        continueButton.click();
    }
}
