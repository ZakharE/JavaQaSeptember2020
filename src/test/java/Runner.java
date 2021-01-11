import io.cucumber.spring.CucumberContextConfiguration;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.springframework.test.context.ContextConfiguration;
import qa.Config;

@CucumberContextConfiguration
@CucumberOptions(features = {"src/test/resources/features"}, glue = {"qa"})
@ContextConfiguration(classes = Config.class)
public class Runner extends AbstractTestNGCucumberTests {
}
