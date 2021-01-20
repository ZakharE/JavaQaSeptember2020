package qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import qa.config.UserConfig;

import javax.annotation.PostConstruct;

@Component
public class AuthPage {

    @Autowired
    private WebDriver driver;

    @Autowired
    private LoginForm loginForm;

    @FindBy(css = "div .header2__auth")
    private WebElement loginButton;
    private final String socialNetworkLocatorPlaceHolder = "a.ic-%s-footer2-hover";
    private final String zenIcon = "a.ic-zen";

    @PostConstruct
    public void init() {
        PageFactory.initElements(driver, this);
    }

    public LoginForm clickLoginButton() {
        loginButton.click();
        return loginForm;
    }

    public void goToMainPage(){
        driver.get(UserConfig.HOST);
    }

    public void goToBlog(){
        driver.get(UserConfig.BLOG);
    }

    public void clickSocialNetworkIcon(String network) {
        if (zenIcon.contains(network)) {
            driver.findElement(By.cssSelector(zenIcon)).click();
            return;
        }
        driver.findElement(By.cssSelector(String.format(socialNetworkLocatorPlaceHolder, network))).click();
    }
}
