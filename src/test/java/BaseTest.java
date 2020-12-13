import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class BaseTest {
    protected static final Logger logger = LogManager.getLogger(HomePageTest.class);
    protected static WebDriver driver;
    protected static final UserConfig cfg = ConfigFactory.create(UserConfig.class);


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
        driver.get(cfg.hostname());
        logger.info("Driver initiated");
        return driver;
    }
}
