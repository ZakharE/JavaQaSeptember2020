package qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class LoginForm {

    @FindBy(css = "input.new-input[name='email']")
    private WebElement emailField;

    @FindBy(css = "input.new-input[name='password']")
    private WebElement passwordField;

    @FindBy(css = "button.new-button.new-button_full.new-button_blue.new-button_md")
    private WebElement signUpButton;

    @Autowired
    private WebDriver driver;

    @Autowired
    PersonalCabinet personalCabinet;

    @PostConstruct
    public void init() {
        PageFactory.initElements(driver, this);
    }

    public void inputEmail(String name) {
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(emailField)).clear();
        emailField.sendKeys(name);
    }

    public void inputPassword(String email) {
        passwordField.clear();
        passwordField.sendKeys(email);
    }

    public PersonalCabinet signUp(String email, String password) {
        inputEmail(email);
        inputPassword(password);
        signUpButton.click();

        return personalCabinet;
    }

}
