package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import constants.EventTime;
import dataproviders.EventCard;
import io.qameta.allure.Step;
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
    private static final String EVENT_NAME = "div.evnt-event-name";
    private static final String EVENT_DATE = "span.date";
    private static final String EVENT_LANGUAGE = "p.language";
    private static final String EVENT_MODE = "div.evnt-card-heading";
    private static final String FILTERS_BUTTON = "//div[contains(@class,'evnt-button')]";
    private static final String LOCATION_FILTER_BUTTON = "filter_location";
    private static final String LOCATION_PLACEHOLDER = "label[data-value='%s']";
    private static final String EVENT_COUNTER = "//span[contains(@class,'evnt-tab-counter')]";
    private static final String EVENT_CARD = "//div[contains(@class,'evnt-event-card')]";

    @Step("Get count of {eventTime}")
    public int getEventListSize(EventTime eventTime) {
        String count = $$x(EVENT_COUNTER)
                .get(eventTime.getId())
                .getText();
        return Integer.parseInt(count);
    }

    public ElementsCollection getEventCards() {
        return $$x(EVENT_CARD);
    }

    @Step("Check page has events")
    public void pageHasEvents(List<EventCard> events) {
        ElementsCollection eventCards = getEventCards();
        eventCards.shouldHaveSize(events.size());

        for (int i = 0; i < events.size(); i++) {
            SelenideElement eventCard = eventCards.get(i);
            EventCard event = events.get(i);
            eventCard.$(EVENT_NAME).shouldHave(text(event.getEventName()));
            eventCard.$(EVENT_DATE).shouldHave(text(event.getDateRange()));
            eventCard.$(EVENT_LANGUAGE).shouldHave(text(event.getLanguage()));
            eventCard.$(EVENT_MODE).shouldHave(text(event.getMode()));
        }
    }

    @Step("Open filters menu")
    public EventsPage openFiltersMenu() {
        $x(FILTERS_BUTTON)
                .shouldBe(visible)
                .click();
        return this;
    }

    @Step("Check event is coming")
    public void eventIsComing() {
        ElementsCollection eventCards = getEventCards();

        eventCards.forEach(card -> {
            String dateRange = card.$(EVENT_DATE).getText().trim();
            assertThat("Date in range", inRange(dateRange));
        });
    }

    @Step("Check that event date '{dateRange} in proper range")
    public boolean inRange(String dateRange) {
        String year = parseYear(dateRange);
        String[] splitedString = dateRange.replaceFirst(YEAR_PATTERN, "").split(" - ");
        int startIndex = 0, endIndex = 1;
        Date start = parseDate(year, startIndex, splitedString);
        Date end = parseDate(year, endIndex, splitedString);
        return start.before(new Date()) && end.after(new Date());
    }

    @Step("Select '{country} at country filter")
    public EventsPage selectCountry(String country) {
        $(By.id(LOCATION_FILTER_BUTTON)).click();
        $(String.format(LOCATION_PLACEHOLDER, country)).click();
        $(By.id(LOCATION_FILTER_BUTTON)).click();
        return this;
    }

    @Step("Open first event")
    public EventPage openFirstEvent() {
        getEventCards().get(0).click();
        return page(EventPage.class);
    }

    private Date parseDate(String source, int i, String[] spliteedString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN, Locale.US);

        try {
            return simpleDateFormat.parse(spliteedString[i].toUpperCase() + " " + source);
        } catch (ParseException e) {
            throw new AssertionError("Unable to parse date");
        }
    }

    private static String parseYear(String dateRange) {
        Pattern compile = Pattern.compile(YEAR_PATTERN);
        Matcher matcher = compile.matcher(dateRange);
        matcher.find();
        return matcher.group();
    }
}

