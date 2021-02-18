package dataproviders;

import org.testng.annotations.DataProvider;

import java.util.Collections;

public class UpcomingEvents {
    @DataProvider(name = "Upcoming events")
    public Object[][] upcomingEvents() {
        return new Object[][]{{
                Collections.singletonList(
                        new EventCard("Check-in app guide",
                                "1 Feb - 25 Aug 2025",
                                "en",
                                "Online only"))
        }
        };
    }
}
