import com.google.common.base.Enums;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class WebDriverFactory {
    private static final Logger logger = LogManager.getLogger(WebDriverFactory.class);

    public static WebDriver create(String browserName) {
        Browser browser = Enums.getIfPresent(Browser.class, browserName.toUpperCase()).orNull();
        if (browser == null) {
            return defaultDriver();
        }

        switch (browser) {
            case CHROME:
                return new ChromeDriver();
            case FIREFOX:
                return new FirefoxDriver();
            default:
                return defaultDriver();
        }
    }

    public static WebDriver create(String browserName, MutableCapabilities options) {
        if (StringUtils.isBlank(browserName)) {
            return defaultDriver();
        }
        Browser browser = Enums.getIfPresent(Browser.class, browserName.toUpperCase()).orNull();
        Browser browserNameFromOptions = Enums.getIfPresent(Browser.class, options.getBrowserName().toUpperCase()).orNull();
        if (browser == null || browserNameFromOptions == null) {
            return defaultDriver();
        }
        if (browser == Browser.CHROME && browserNameFromOptions == Browser.CHROME) {
            return new ChromeDriver((ChromeOptions) options);
        }
        if (browser == Browser.FIREFOX && browserNameFromOptions == Browser.FIREFOX) {
            return new FirefoxDriver((FirefoxOptions) options);
        }
        throw new WebDriverException("You used wrong type of options for your driver");
    }

    private static WebDriver defaultDriver() {
        logger.warn("Your driver is unsupported. Please check your input. Chrome driver will be used.");
        return new ChromeDriver();
    }
}