package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class BasePage {
    protected WebDriver driver;
    protected static final Logger logger = LogManager.getLogger(BasePage.class);
    protected static final int DEFAULT_AWAIT = 5;
    public BasePage(WebDriver driver) {
        this.driver = driver;
    }
}
