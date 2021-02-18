package pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class EventTalkCard {

    private static final String CARD_LOCATION = "//div[contains(@class,'location')]";
    private static final String CARD_LANGUAGE = "//div[contains(@class,'language')]";
    private static final String CARD_CATEGORY = "div.evnt-talk-details.topics";

    @Step("Event talk card has location '{location}'")
    public EventTalkCard hasLocation(String location) {
        $x(CARD_LOCATION)
                .shouldNot(empty)
                .shouldHave(text(location));
        return this;
    }

    @Step("Event talk card has language '{language}'")
    public EventTalkCard hasLanguage(String language) {
        $x(CARD_LANGUAGE)
                .shouldNot(empty)
                .shouldHave(text(language));
        return this;
    }

    @Step("Event talk card has category '{category}'")
    public EventTalkCard hasCategory(String category) {
        $(CARD_CATEGORY)
                .shouldNot(empty)
                .shouldHave(text(category));
        return this;
    }
}
