package qa.stepsDefs;

import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import qa.pages.AuthPage;
import qa.pages.BlogPage;

public class WhenDefs {
    @Autowired
    AuthPage authPage;
    @Autowired
    BlogPage blogPage;

    @When("Login with email {string} and password {string}")
    public void loginAsUserWithPasswordPassword(String email, String password) {
        authPage.clickLoginButton()
                .signUp(email, password);
    }

    @When("Click {string} icon")
    public void clickIcon(String network) {
        authPage.clickSocialNetworkIcon(network);
    }

    @When("I go to blog page")
    public void iGoToBlogPage() {
        authPage.goToBlog();
    }

    @When("I add/remove article to favorites")
    public void iAddArticleToFavorites() {
        blogPage.addFirstPostToFavorites();
    }
}
