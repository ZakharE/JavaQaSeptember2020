package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import dataproviders.EventCard;

import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class EventsPage {

    public int getEventSize() {
        String count = $x("//span[contains(@class,'evnt-tab-counter')]").getText();
        return Integer.parseInt(count);
    }

    public ElementsCollection getEventCards(){
        return $$x("//div[contains(@class,'evnt-event-card')]");
    }

    public void pageHasEvents(List<EventCard> events) {
        ElementsCollection eventCards = getEventCards();
        eventCards.shouldHaveSize(events.size());

        for (int i = 0; i < events.size(); i++) {
            SelenideElement eventCard = eventCards.get(i);
            EventCard event = events.get(i);
            eventCard.$("div.evnt-event-name").shouldHave(text(event.getEventName()));
            eventCard.$("span.date").shouldHave(text(event.getDateRange()));
            eventCard.$("p.language").shouldHave(text(event.getLanguage()));
            eventCard.$("div.evnt-card-heading").shouldHave(text(event.getMode()));
        }
    }
}

