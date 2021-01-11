package qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class PersonalCabinet  {
    @Autowired
    WebDriver driver;
    private By loginButton;

    public boolean loggedIn() {
        loginButton = By.cssSelector("button[data-modal-id='new-log-reg']");
        return new WebDriverWait(driver, 3).until(ExpectedConditions.invisibilityOfElementLocated(loginButton));
    }

    public boolean notLogged() {
        return new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOfElementLocated(By.className("new-input-error"))).isDisplayed();
    }
}
