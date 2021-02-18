package pages;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class MainPage {

    private static final String UPCOMING_EVENTS = "UPCOMING EVENTS";
    private static final String PAST_EVENTS = "PAST EVENTS";
    private static final String ACCEPT_COOKIE_BUTTON = "onetrust-accept-btn-handler";
    private static final String ALERT_POP_UP = "div.evnt-alert-wrapper";
    private static final String CLOSE_ALERT_POP_UP_BUTTON = "button[class='close']";
    private static final String VIDEO = "VIDEO";

    @Step("Go to upcoming events")
    public EventsPage goToUpcomingEventsPage() {
        $(By.partialLinkText(UPCOMING_EVENTS)).click();
        return page(EventsPage.class);
    }

    @Step("Go to past events")
    public EventsPage goToPastEventsPage() {
        goToUpcomingEventsPage();
        $(By.partialLinkText(PAST_EVENTS)).click();
        return page(EventsPage.class);
    }

    @Step("Close pop-ups")
    public MainPage closePopUps() {
        $(By.id(ACCEPT_COOKIE_BUTTON)).shouldBe(Condition.appear).click();
        $(ALERT_POP_UP)
                .shouldBe(Condition.appear)
                .$(CLOSE_ALERT_POP_UP_BUTTON)
                .click();
        return this;
    }

    @Step("Go to video page")
    public VideoPage goToVideoPage() {
        $(By.partialLinkText(VIDEO)).should(Condition.appear).click();
        return page(VideoPage.class);
    }
}
