import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import java.net.URI;

@Listeners({ScreenshotListener.class})
public class BaseTest {
    protected static final Logger logger = LogManager.getLogger(HomePageTest.class);
    protected static WebDriver driver;

    @BeforeMethod
    public void setUp() {
        createNewSession();
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Driver was closed");
        }
    }

    @lombok.SneakyThrows
    public WebDriver createNewSession() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "chrome");
        capabilities.setCapability("browserVersion", "85.0");
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        capabilities.setCapability("enableLogs", true);
        driver = new RemoteWebDriver(
                URI.create("http://localhost:4444/wd/hub").toURL(),
                capabilities
        );

        driver.manage().window().maximize();
        driver.get(UserConfig.HOSTNAME);
        logger.info("Driver initiated");
        return driver;
    }

    public static WebDriver getDriver() {
        return driver;
    }
}
