import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.util.HashMap;
import java.util.Map;

public class BaseTest {
    protected static final Logger logger = LogManager.getLogger(HomePageTest.class);


    @BeforeMethod
    public void setUp() {
       createNewSession();
    }

    public void createNewSession() {
        WebDriverManager.chromedriver().setup();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("safebrowsing.enabled", true);
        prefs.put("profile.default_content_settings.popups", 0);

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("disable-extensions");
        options.addArguments("safebrowsing-disable-extension-blacklist");
        options.addArguments("safebrowsing-disable-download-protection");

        Configuration.browserCapabilities.setCapability(ChromeOptions.CAPABILITY, options);        logger.info("Driver initiated");
        Selenide.open("https://events.epam.com/");
        Selenide.clearBrowserCookies();
    }

    @AfterMethod
    public void tearDown() {
        Selenide.closeWebDriver();
    }
}
