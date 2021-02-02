package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AuthPage extends BasePage{

    public AuthPage(WebDriver driver) {
        super(driver);
    }

    private final By loginButton = By.cssSelector("div .header2__auth");

    @Step("Click login button")
    public LoginForm clickLoginButton() {
        driver.findElement(loginButton).click();
        logger.info("Login form was opened");
        return new LoginForm(driver);
    }
}
