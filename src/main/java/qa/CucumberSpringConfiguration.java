package qa;

import io.cucumber.java.After;
import io.cucumber.spring.CucumberContextConfiguration;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.AfterTest;

@CucumberContextConfiguration
@ContextConfiguration(classes = Config.class)
@ComponentScan
public class CucumberSpringConfiguration {
    @Autowired
    public WebDriver driver;

    @After
    public void tearDown() {
        driver.manage().deleteAllCookies();
    }
    @AfterTest
    public void asd() {
        driver.quit();
    }
}