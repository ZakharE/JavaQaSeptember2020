package pages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class MainPage {
    public EventsPage goToUpcomingEventsPage() {

        $(By.partialLinkText("UPCOMING EVENTS")).click();
        return page(EventsPage.class);
    }

    public EventsPage goToPastEventsPage() {
        goToUpcomingEventsPage();
        $(By.partialLinkText("PAST EVENTS")).click();
        return page(EventsPage.class);
    }

    public MainPage closePopUps() {
        $(By.id("onetrust-accept-btn-handler")).shouldBe(Condition.visible).click();
        $("div.evnt-alert-wrapper")
                .shouldBe(Condition.visible)
                .$("button[class='close']")
                .click();
        return this;
    }

}
