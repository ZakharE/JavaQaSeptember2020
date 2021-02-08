package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class MainPage {
    public EventsPage goToEventsPage() {
        //document.getElementById("onetrust-banner-sdk").style.display ="none"
        $(By.id("onetrust-accept-btn-handler")).shouldBe(Condition.visible).click();
        $("div.evnt-alert-wrapper")
                .shouldBe(Condition.visible)
                .$("button[class='close']")
                .click();
        $(By.partialLinkText("UPCOMING EVENTS")).click();
        return page(EventsPage.class);
    }
}
