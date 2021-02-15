import com.codeborne.selenide.CollectionCondition;
import constants.EventTime;
import dataproviders.EventCard;
import dataproviders.UpcomingEvents;
import org.testng.annotations.Test;
import pages.EventsPage;
import pages.MainPage;

import java.util.List;

public class HomePageTest extends BaseTest {


    @Test(description = "Check proper events size")
    public void checkProperEventsSize() {
        EventsPage eventsPage = new MainPage()
                .closePopUps()
                .goToUpcomingEventsPage();
        int eventSize = eventsPage
                .getEventListSize(EventTime.UPCOMING);

        eventsPage.getEventCards().shouldHaveSize(eventSize);
    }

    @Test(description = "Check events content", dataProviderClass = UpcomingEvents.class, dataProvider = "Upcoming events")
    public void checkEventContent(List<EventCard> events) {
        new MainPage()
                .closePopUps()
                .goToUpcomingEventsPage()
                .pageHasEvents(events);
    }

    @Test(description = "Check events date")
    public void checkEventDate() {
        EventsPage eventsPage = new MainPage()
                .closePopUps()
                .goToUpcomingEventsPage();

        eventsPage.eventIsComing();
    }

    @Test(description = "Check past events")
    public void checkPastEventDate() {
        EventsPage eventsPage = new MainPage()
                .closePopUps()
                .goToPastEventsPage()
                .openFiltersMenu()
                .selectCountry("Canada");
        int eventSize = eventsPage
                .getEventListSize(EventTime.PAST);
        eventsPage.getEventCards().shouldHaveSize(eventSize);
    }

    @Test(description = "Check detailed event info")
    public void checkDetailedEventInfo() {
        EventsPage eventsPage = new MainPage()
                .closePopUps()
                .goToUpcomingEventsPage();
        eventsPage.getEventCards().shouldHave(CollectionCondition.sizeGreaterThan(0));
        eventsPage.openFirstEvent()
                .hasSaveToCalendarButton()
                .hasName("Check-in app guideThank you for being a volunteer!");
    }

    @Test(description = "Check query search")
    public void checkQuerySearchrForVideos() {
        new MainPage()
                .closePopUps()
                .goToVideoPage()
                .inputSearchQuery("QA")
                .eventCardsShouldContainKeyword("QA");
    }

    @Test(description = "Check video search filters")
    public void checkFiltersForVideoPage() {
        String language = "ENGLISH";
        String location = "Belarus";
        String category = "Testing";
        new MainPage()
                .closePopUps()
                .goToVideoPage()
                .openFilters()
                .selectLanguage(language)
                .selectLocation(location)
                .selectCategory(category)
                .checkAllCardsHas(language, location, category);
    }
}