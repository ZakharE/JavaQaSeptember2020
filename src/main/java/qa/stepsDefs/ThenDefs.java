package qa.stepsDefs;

import io.cucumber.java.en.Then;
import org.hamcrest.Matchers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import qa.pages.PersonalCabinet;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;
import static qa.config.UserConfig.BLOG_FAVORITES;

public class ThenDefs {
    @Autowired
    PersonalCabinet personalCabinet;
    @Autowired
    WebDriver driver;

    @Then("I logged in")
    public void iLoggedInAs() {
        assertTrue(personalCabinet.loggedIn());
    }

    @Then("I am not logged")
    public void iAmNotLogged() {
        assertTrue(personalCabinet.notLogged());
    }

    @Then("New tab with {string} is opened")
    public void newTabWithIsOpened(String url) {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        assertThat(tabs, Matchers.hasSize(2));
        driver.switchTo().window(tabs.get(1));
        assertThat(url, Matchers.equalTo(driver.getCurrentUrl()));
        driver.switchTo().window(tabs.get(1)).close();
        driver.switchTo().window(tabs.get(0));

    }

    @Then("Article added to favorites section")
    public void articleAddedToFavoritesSection() {
        driver.get(BLOG_FAVORITES);
        int postsNumber = driver.findElements(By.cssSelector("div[class='blog-post']")).size();
        assertThat(postsNumber, Matchers.equalTo(1));
    }
}
