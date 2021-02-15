package pages;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class EventPage {
    private final static String ADD_TO_CALENDAR = "//div[contains(@class,'add-to-calendar')]";
    private final static String EVENT_NAME = "//div[contains(@class,'hero')]";

    @Step("Check page has 'Save to calendar' button")
    public EventPage hasSaveToCalendarButton() {
        $x(ADD_TO_CALENDAR).shouldBe(Condition.visible).shouldBe(Condition.exist);
        return this;
    }

    @Step("Check page has event name '{name}'")
    public void hasName(String name) {
        $x(EVENT_NAME).should(Condition.text(name));
    }
}
