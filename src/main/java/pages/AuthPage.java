package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AuthPage extends BasePage{

    public AuthPage(WebDriver driver) {
        super(driver);
    }

    private final By loginButton = By.cssSelector("div .header2__auth");

    public LoginForm clickLoginButton() {
        driver.findElement(loginButton).click();
        logger.info("Login form was opened");
        return new LoginForm(driver);
    }
}
