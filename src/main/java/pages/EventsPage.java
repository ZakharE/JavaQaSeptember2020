package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import constants.EventTime;
import dataproviders.EventCard;
import org.hamcrest.Matchers;
import org.openqa.selenium.By;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class EventsPage {
    private static final String DATE_PATTERN = "dd MMM yyyy";
    private static final String YEAR_PATTERN = "\\d{4}";


    public int getEventListSize(EventTime eventTime) {
        String count = $$x("//span[contains(@class,'evnt-tab-counter')]")
                .get(eventTime.getId())
                .getText();
        return Integer.parseInt(count);
    }

    public ElementsCollection getEventCards() {
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

    public EventsPage openFiltersMenu() {
        $x("//div[contains(@class,'evnt-button')]")
                .shouldBe(visible)
                .click();
        return this;
    }

    public void eventIsComing() {
        ElementsCollection eventCards = getEventCards();

        eventCards.forEach(card -> {
            String dateRange = card.$("span.date").getText().trim();
            assertThat("Date in range", inRange(dateRange));
        });
    }

    public boolean inRange(String dateRange) {
        String year = parseYear(dateRange);
        String[] splitedString = dateRange.replaceFirst(YEAR_PATTERN, "").split(" - ");
        int startIndex = 0, endIndex = 1;
        Date start = parseDate(year, startIndex, splitedString);
        Date end = parseDate(year, endIndex, splitedString);
        return start.before(new Date() )&& end.after(new Date());
    }

    private Date parseDate(String source, int i, String[] spliteedString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN, Locale.US);

        try {
            return simpleDateFormat.parse(spliteedString[i].toUpperCase() + " " + source);
        } catch (ParseException e) {
            throw new AssertionError("Unable to parse date");
        }
    }

    public static String parseYear(String dateRange) {
        Pattern compile = Pattern.compile(YEAR_PATTERN);
        Matcher matcher = compile.matcher(dateRange);
        matcher.find();
        return matcher.group();
    }

    public EventsPage selectCountry(String country) {
        $(By.id("filter_location")).click();
        $(String.format("label[data-value='%s']", country)).click();
        $(By.id("filter_location")).click();
        return this;
    }

    public EventPage openFirstEvent() {
        getEventCards().get(0).click();
        return page(EventPage.class);
    }
}

