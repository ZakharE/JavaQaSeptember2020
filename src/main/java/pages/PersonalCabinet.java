package pages;

import constants.SocialNetworks;
import org.hamcrest.Matchers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.not;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class PersonalCabinet extends BasePage {
    private final By genderField = By.id("id_gender");
    private final By companyField = By.cssSelector("input[name='company']");
    private final By companyPositionField = By.id("id_work");
    private final String contactFieldPostionPlaceHolder = "div[data-num='%s']";
    private final By countryField = By.xpath("//input[@name='country']/../..");
    private final By englishLevelField = By.xpath("//input[@name='english_level']/../..");
    private final By lastNameField = By.id("id_lname");
    private final By saveButton = By.xpath("//button[@title='Сохранить и продолжить']");
    private final By addContactButton = By.xpath("//button[contains(text(), 'Добавить')]");
    private static final String buttonTitlePlaceHolder = "button[title='%s']";
    private final String contactValuePlaceholder = "id_contact-%s-value";
    private final By cityField = By.xpath("//input[@name='city']/../..");

    public PersonalCabinet(WebDriver driver) {
        super(driver);
    }

    public PersonalCabinet goToAboutSection() {
        new WebDriverWait(driver, DEFAULT_AWAIT).until(elementToBeClickable(By.cssSelector("div.header2-menu__item-wrapper.header2-menu__item-wrapper__username"))).click();
        driver.findElement(By.partialLinkText("Мой профиль")).click();
        new WebDriverWait(driver, DEFAULT_AWAIT).until(urlContains("personal"));
        logger.info("Section 'Мой профиль' was opened");
        return this;
    }

    public PersonalCabinet fillLastName(String lastName) {
        WebElement element = new WebDriverWait(driver, DEFAULT_AWAIT).until(elementToBeClickable(lastNameField));
        element.clear();
        element.sendKeys(lastName);
        logger.info("Last name set to '{}'", lastName);
        return this;
    }

    public PersonalCabinet selectCountry(String country) {
        new WebDriverWait(driver, DEFAULT_AWAIT).until(elementToBeClickable(countryField)).click();
        new WebDriverWait(driver, DEFAULT_AWAIT).until(elementToBeClickable(
                By.cssSelector(String.format(buttonTitlePlaceHolder, country)))).click();

        new WebDriverWait(driver, DEFAULT_AWAIT).until(not(elementToBeClickable(By.cssSelector(String.format(buttonTitlePlaceHolder, "Не выбрано")))));
        logger.info("Country was set to '{}'", country);
        return this;
    }

    public PersonalCabinet selectCity(String city) {
        new WebDriverWait(driver, DEFAULT_AWAIT)
                .until(elementToBeClickable(cityField))
                .click();
        new WebDriverWait(driver, DEFAULT_AWAIT).until(visibilityOfElementLocated(
                By.cssSelector(String.format(buttonTitlePlaceHolder, city)))).click();
        new WebDriverWait(driver, DEFAULT_AWAIT).until(not(elementToBeClickable(By.cssSelector(String.format(buttonTitlePlaceHolder, "Не выбрано")))));
        logger.info("Country was set to '{}'", city);

        return this;
    }

    public PersonalCabinet selectEnglishLevel(String level) {
        driver.findElement(englishLevelField).click();
        driver.findElement(By.cssSelector(String.format(buttonTitlePlaceHolder, level))).click();
        logger.info("English level was set to '{}'", level);

        return this;
    }

    public PersonalCabinet selectWorkSchedule(String schedule) {
        driver.findElement(By.xpath(String.format("//input[@title='%s']/..", schedule))).click();
        logger.info("Schedule was set to '{}'", schedule);
        return this;
    }

    public PersonalCabinet inputContact(int contactListNumber, SocialNetworks contact) {
        WebElement contactContainer = driver.findElement(By.cssSelector(String.format(contactFieldPostionPlaceHolder, contactListNumber)));
        contactContainer.findElement(By.xpath("//span[contains(text(),\"Способ связи\")]/..")).click();
        contactContainer.findElement(By.cssSelector(String.format(buttonTitlePlaceHolder, contact.getName()))).click();
        contactContainer.findElement(By.id(String.format(contactValuePlaceholder, contactListNumber))).sendKeys(contact.getValue());
        logger.info("Contact '{}' was set to '{}'", contact.getName(), contact.getValue());

        return this;
    }

    public PersonalCabinet addContact() {
        driver.findElement(addContactButton).click();
        logger.info("New contact section was added");
        return this;
    }

    public PersonalCabinet selectGender(String gender) {
        Select dropdownList = new Select(driver.findElement(genderField));
        dropdownList.selectByVisibleText(gender);
        logger.info("Gender was set to {}", gender);
        return this;
    }

    public PersonalCabinet inputCompany(String company) {
        WebElement element = driver.findElement(companyField);
        element.clear();
        element.sendKeys(company);
        logger.info("Company was set to {}", company);

        return this;
    }

    public PersonalCabinet inputCompanyPosition(String position) {
        WebElement element = driver.findElement(companyPositionField);
        element.clear();
        element.sendKeys(position);
        logger.info("Company position was set to {}", position);

        return this;
    }

    public void save() {
        new WebDriverWait(driver, DEFAULT_AWAIT).until(elementToBeClickable(saveButton))
                .click();
        new WebDriverWait(driver, DEFAULT_AWAIT*3).until(urlContains("skills"));

        logger.info("Info has been saved");
    }

    public PersonalCabinet assertCityIs(String city) {
        String elementText = new WebDriverWait(driver, DEFAULT_AWAIT).until(elementToBeClickable(
                cityField)).getText();
        assertThat(elementText, containsString(city));
        return this;
    }

    public PersonalCabinet assertCountryIs(String country) {
        assertThat(driver.findElement(countryField).getText().trim(), equalTo(country));
        return this;
    }

    public PersonalCabinet assertEnglishLevelIs(String englishLevel) {
        assertThat(driver.findElement(englishLevelField).getText().trim(), equalTo(englishLevel));

        return this;
    }

    public PersonalCabinet assertLastNameIs(String lastName) {
        assertThat(getFieldValue(lastNameField), equalTo(lastName));
        return this;
    }

    public PersonalCabinet assertWorkScheduleIs(String workSchedule) {
        String workSchedulePlaceHolder = "//input[@title='%s']";
        assertThat(driver.findElement(By.xpath(String.format(workSchedulePlaceHolder, workSchedule))).isSelected(), is(true));
        return this;
    }

    public PersonalCabinet assertCompanyIs(String company) {
        assertThat(getFieldValue(companyField), equalTo(company));
        return this;
    }

    public PersonalCabinet assertCompanyPositionIs(String companyPosition) {
        assertThat(getFieldValue(companyPositionField), equalTo(companyPosition));
        return this;
    }

    public PersonalCabinet assertGenderIs(String gender) {
        Select dropdownList = new Select(driver.findElement(genderField));
        assertThat(dropdownList.getAllSelectedOptions(), Matchers.<WebElement>hasSize(1));
        assertThat(dropdownList.getFirstSelectedOption().getText().trim(), equalTo(gender));
        return this;
    }

    private String getFieldValue(By locator) {
        return driver.findElement(locator).getAttribute("value").trim();
    }

    public PersonalCabinet assertHasContact(int contactListNumber, SocialNetworks contact) {
        WebElement contactContainer = driver.findElement(By.cssSelector(String.format(contactFieldPostionPlaceHolder, contactListNumber)));
        contactContainer.findElement(By.cssSelector("div.input")).getText();
        assertThat(contactContainer.findElement(By.cssSelector("div.input")).getText().trim(), equalTo(contact.getName()));
        String fieldValue = getFieldValue(By.id(String.format(contactValuePlaceholder, contactListNumber)));
        assertThat(fieldValue, equalTo(contact.getValue()));
        return this;
    }
}
