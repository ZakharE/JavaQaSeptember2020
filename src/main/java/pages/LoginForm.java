package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginForm extends BasePage{
    By emailField = By.cssSelector("input.new-input[name='email']");
    By passwordField = By.cssSelector("input.new-input[name='password']");
    By signUpButton = By.cssSelector("button.new-button.new-button_full.new-button_blue.new-button_md");
    public LoginForm(WebDriver driver) {
        super(driver);
    }

    public void inputEmail(String name) {
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(emailField)).clear();
        driver.findElement(emailField).sendKeys(name);
    }

    public void inputPassword(String email) {
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(email);
    }

    public PersonalCabinet signUp(String email, String password) {
        inputEmail(email);
        inputPassword(password);
        driver.findElement(signUpButton).click();

        return new PersonalCabinet(driver);
    }

}
