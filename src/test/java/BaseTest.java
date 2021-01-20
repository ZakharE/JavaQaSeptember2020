import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;

@Listeners({ScreenshotListener.class})
public class BaseTest {
    protected static final Logger logger = LogManager.getLogger(HomePageTest.class);
    protected static WebDriver driver;

    @BeforeTest
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

    public WebDriver createNewSession() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(UserConfig.HOSTNAME);
        logger.info("Driver initiated");
        return driver;
    }

    public static WebDriver getDriver() {
        return driver;
    }
}
