package qa.stepsDefs;

import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import qa.pages.AuthPage;

public class GivenDefs {
    @Autowired
    AuthPage authPage;

    @Given("I am on the main page")
    public void mainPage() {
        authPage.goToMainPage();
    }
}
