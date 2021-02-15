import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

public class BaseTest {
    protected static final Logger logger = LogManager.getLogger(HomePageTest.class);


    @BeforeMethod
    public void setUp() {
       createNewSession();
    }

    public void createNewSession() {
        WebDriverManager.chromedriver().setup();

        WebDriverRunner.setWebDriver(new ChromeDriver());
        logger.info("Driver initiated");
        Selenide.open("https://events.epam.com/");
        Selenide.clearBrowserCookies();
    }

    @AfterMethod
    public void tearDown() {
        Selenide.closeWebDriver();
    }
}
