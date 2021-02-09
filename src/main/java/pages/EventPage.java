package pages;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.$x;

public class EventPage {
    public EventPage hasSaveToCalendarButton(){
        $x("//div[contains(@class,'add-to-calendar')]").shouldBe(Condition.visible).shouldBe(Condition.exist);
        return this;
    }
}
