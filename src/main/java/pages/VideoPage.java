package pages;

import com.codeborne.selenide.*;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThanOrEqual;
import static com.codeborne.selenide.Selenide.*;

public class VideoPage {

    private static final String SEARCH_STRING_QUERY_FIELD = "//div[contains(@class,'evnt-results-cell')]/p/span";
    private static final String SEARCH_STRING_QUERY_SECTION = "input[type='text']";
    private static final String RESULT_SEARCH = "//div[contains(@class,'evnt-results-cell')]";
    private static final String VIDEO_NAME = "div.evnt-talk-name";
    private static final String VIDEOS_SELECTOR = "div.evnt-talk-card";
    private static final String FILTERS_BUTTON = "//div[contains(@class,'evnt-toggle-filters-button')]";
    private static final String FILTER_LOCATION = "filter_location";
    private static final String LOCATION_PLACEHOLDER = "label[data-value='%s']";
    private static final String FILTER_LANGUAGE = "filter_language";
    private static final String FILTER_LANGUAGE_PLACEHOLDER = "label[data-value='%s']";
    private static final String FILTER_CATEGORY = "filter_category";
    private static final String FILTER_CATEGORY_PLACEHOLDER = "label[data-value='%s']";

    @Step("Input query '{query}'")
    public VideoPage inputSearchQuery(String query) {
        String text = $x(SEARCH_STRING_QUERY_FIELD)
                .shouldBe(Condition.visible).shouldNot(Condition.empty).getText();
        $(SEARCH_STRING_QUERY_SECTION).sendKeys(query, Keys.ENTER);
        $x(RESULT_SEARCH).shouldNot(Condition.text(text));
        return this;
    }

    @Step("Input query '{query}'")
    public VideoPage eventCardsShouldContainKeyword(String keyword) {
        getEventTalkCards().forEach(eventTalkCard -> {
            eventTalkCard.$(VIDEO_NAME).shouldHave(Condition.text(keyword));
        });
        return this;
    }

    @Step("Get event talk cards")
    public ElementsCollection getEventTalkCards() {
        return $$(VIDEOS_SELECTOR).shouldHave(sizeGreaterThanOrEqual(1));
    }

    @Step("Open filters")
    public VideoPage openFilters() {
        $x(FILTERS_BUTTON).click();
        return this;
    }

    @Step("Select {location} at location filter")
    public VideoPage selectLocation(String location) {
        $(By.id(FILTER_LOCATION)).shouldBe(Condition.visible).click();
        $(String.format(LOCATION_PLACEHOLDER, location)).click();
        $(By.id(FILTER_LOCATION)).shouldBe(Condition.visible).click();
        return this;
    }

    @Step("Select {language} at language filter")
    public VideoPage selectLanguage(String language) {
        $(By.id(FILTER_LANGUAGE)).shouldBe(Condition.visible).click();
        $(String.format(FILTER_LANGUAGE_PLACEHOLDER, language)).click();
        $(By.id(FILTER_LANGUAGE)).shouldBe(Condition.visible).click();
        return this;
    }

    @Step("Select {category} at category filter")
    public VideoPage selectCategory(String category) {
        $(By.id(FILTER_CATEGORY)).shouldBe(Condition.visible).click();
        $(String.format(FILTER_CATEGORY_PLACEHOLDER, category)).click();
        $(By.id(FILTER_CATEGORY)).shouldBe(Condition.visible).click();
        return this;
    }

    @Step("Check all cards have language = {language}, location = {location}, category = {category}")
    public VideoPage checkAllCardsHas(String language, String location, String category) {
        int eventsSize = getEventTalkCards().size();
        for (int i = 0; i < eventsSize; i++) {
            SelenideElement card = getEventTalkCards().shouldBe(CollectionCondition.sizeGreaterThan(0)).get(i);
            card.shouldBe(Condition.visible).should(Condition.appear).click();
            EventTalkCard eventTalkCard = page(EventTalkCard.class);
            eventTalkCard
                    .hasLocation(location)
                    .hasLanguage(language)
                    .hasCategory(category);
            Selenide.back();
        }
        return this;
    }
}
