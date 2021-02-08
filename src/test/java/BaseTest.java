import com.codeborne.selenide.Selenide;
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


    @BeforeTest
    public void setUp() {
       createNewSession();
    }

    public void createNewSession() {
        WebDriverManager.chromedriver().setup();
//        driver.manage().window().maximize();
//        driver.get(cfg.hostname());
        logger.info("Driver initiated");
        Selenide.open("https://events.epam.com/");
    }
}
