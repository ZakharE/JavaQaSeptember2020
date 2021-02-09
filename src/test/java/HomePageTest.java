import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
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

    @Test
    public void checkDetailedEventInfo() {
        EventsPage eventsPage = new MainPage()
                .closePopUps()
                .goToUpcomingEventsPage();
        eventsPage.getEventCards().shouldHave(CollectionCondition.sizeGreaterThan(0));
        eventsPage.openFirstEvent()
                .hasSaveToCalendarButton();
    }

//5. Просмотр детальной информации о мероприятии:
//5.1 Пользователь переходит на вкладку events
//5.2 Пользователь нажимает на Upcoming Events
//5.3 На странице отображаются карточки предстоящих мероприятий.
//5.4 Пользователь нажимает на любую карточку
//5.5 Происходит переход на страницу с подробной информацией о мероприятии
//5.6 На странице с информацией о мероприятии отображается блок с кнопкой для регистрации, дата и время, программа мероприятия.
}
