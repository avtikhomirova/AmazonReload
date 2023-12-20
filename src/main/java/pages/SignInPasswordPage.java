package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignInPasswordPage extends Page{
    WebDriver driver;
    public SignInPasswordPage(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@id=\"ap_password\"]")
    private WebElement passwordInput;
    By passwordInputBy = By.xpath("//input[@id=\"ap_password\"]");
    @FindBy(name = "rememberMe")
    private WebElement rememberMeCheckbox;
    By rememberMeCheckboxBy = By.name("rememberMe");
    @FindBy(id = "signInSubmit")
    private WebElement signInButton;
    By signInButtonBy = By.id("signInSubmit");


    public void fillPasswordForSignIn(String password){
        //Insert the password
        waitForElementToAppear(passwordInputBy);
        passwordInput.sendKeys(password);

        //Click on checkbox "Keep me signed in"
        waitForElementToAppear(rememberMeCheckboxBy);
        rememberMeCheckbox.click();

        //Button "Sign in" button
        signInButton.click();
    }
}
