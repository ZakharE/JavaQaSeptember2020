import com.codeborne.selenide.Selenide;
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
                .goToEventsPage();
        int eventSize = eventsPage
                .getEventSize();

        eventsPage.getEventCards().shouldHaveSize(eventSize);
    }

    @Test(description = "Check events content", dataProviderClass = UpcomingEvents.class, dataProvider = "Upcoming events")
    public void checkEventContent(List<EventCard> events) {
        new MainPage()
                .goToEventsPage()
                .pageHasEvents(events);
    }
//    2. Просмотр карточек мероприятий:
//2.1 Пользователь переходит на вкладку events
//2.2 Пользователь нажимает на Upcoming Events
//2.3 На странице отображаются карточки предстоящих мероприятий.
//2.4 В карточке указана информация о мероприятии:
}
