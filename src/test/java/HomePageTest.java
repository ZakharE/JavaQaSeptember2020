import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matchers;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class HomePageTest {

    private static final Logger logger = LogManager.getLogger(HomePageTest.class);
    private static WebDriver driver;
    private static final DomainConfig cfg = ConfigFactory.create(DomainConfig.class);

    private final static String HOMEPAGE_TITLE = "Онлайн‑курсы для профессионалов, дистанционное обучение современным профессиям";

    @BeforeTest
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = WebDriverFactory.create(System.getProperty("browser"));
        logger.info("Driver initiated");
    }

    @Test
    public void openHomePage() {
        driver.get(cfg.hostname());
        logger.info("Main page was open");
        assertThat(driver.getTitle(), Matchers.equalTo(HOMEPAGE_TITLE));
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Driver was closed");
        }
    }
}
