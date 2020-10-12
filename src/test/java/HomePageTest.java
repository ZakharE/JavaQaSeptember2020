import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matchers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class HomePageTest {

    private static final Logger logger = LogManager.getLogger(HomePageTest.class);
    protected static WebDriver driver;
    private final static String HOMEPAGE_TITLE = "Онлайн‑курсы для профессионалов, дистанционное обучение современным профессиям";

    @BeforeTest
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        logger.info("Driver initiated");
    }

    @Test
    public void openHomePage() {
        driver.get("https://otus.ru");
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
