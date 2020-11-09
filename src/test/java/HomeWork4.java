import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class HomeWork4 {
    private static final Logger logger = LogManager.getLogger(HomePageTest.class);
    private static WebDriver driver;
    private static final DomainConfig cfg = ConfigFactory.create(DomainConfig.class);
    private final int TIMEOUT = 10;
    private String SAINT_PETERSBURG = "city7800000000000";

    @BeforeTest
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        logger.info("Driver initiated");
    }

    @Test
    public void addTwoItemsToCart() {
        driver.get(cfg.voltMainPage());
        selectCity(SAINT_PETERSBURG);
        selectCategory("Электроинструменты");
        selectCategory("Перфораторы");
        waitElementToBeClickable(By.cssSelector("div.producer-list-toggle"));
        addProductToFilter(Instruments.MAKITA);
        addProductToFilter(Instruments.ZUBR);
        applyFilter();
        sortBy(By.cssSelector("ul[class='select2-results__options']> li:nth-child(1)"));

        String zubrId = addProductToCompareList(Instruments.ZUBR);
        String makitaId = addProductToCompareList(Instruments.MAKITA);
        goToCompareList();
        assertCompareListHasProductWithId(makitaId);
        assertCompareListHasProductWithId(zubrId);
    }

    private void assertCompareListHasProductWithId(String id) {
        List<WebElement> products = driver.findElements(By.id(id + "_compare"));
        assertThat(products, hasSize(1));
    }

    private void goToCompareList() {
        driver.findElement(By.id("cCountCompare")).click();
        logger.info("Going to compare list");
    }

    private String addProductToCompareList(Instruments product) {
        WebElement item = driver.findElements(By.xpath(String.format("//ul/li/*[contains(text(), '%s')]/..", product.getInstrument()))).get(0);
        String id = item.findElement(By.cssSelector(".new-item-list-name > a")).getAttribute("data-code");
        new WebDriverWait(driver, TIMEOUT)
                .until(ExpectedConditions.elementToBeClickable(item.findElement(By.cssSelector("div.new-item-list-compare")))).click();
        By closePopUpCross = By.cssSelector(".ui-button");
        closePopUpAndClick(closePopUpCross);
        logger.info("Product {} was added to compare list", product);

        return id;
    }

    private void closePopUpAndClick(By locator) {
        logger.info("wait for loading all js elements");
        sleepFor(2);
        logger.info("end of waiting");

        JavascriptExecutor scriptExecutor = (JavascriptExecutor) HomeWork4.driver;
        String removePopUpCommand = "Array.from(document.getElementsByClassName('%s')).forEach(e => e.remove());";
        scriptExecutor.executeScript(String.format(removePopUpCommand, "flocktory-widget"));
        scriptExecutor.executeScript(String.format(removePopUpCommand, "flocktory-widget-overlay"));
        logger.info("PopUps were closed");

        new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    private void sleepFor(int seconds) {
        try {
            Thread.sleep(seconds* 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sortBy(By sortSelector) {
        closePopUpAndClick(By.cssSelector("span[class='select2-selection__rendered'"));
        driver.findElement(sortSelector).click();
        new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("#product-list > li"), 0));
        logger.info("Items were sorted with selector '{}'", sortSelector.toString());
    }

    private void applyFilter() {
        driver.findElement(By.cssSelector("input[value='Подобрать модель']")).click();
        logger.info("Filter was applied");
    }

    private void addProductToFilter(Instruments product) {
        driver.findElement(By.cssSelector(String.format("input[title='%s']", product.getInstrument()))).click();
        logger.info("Product '{}' was added to filter", product);
    }

    private void waitElementToBeClickable(By selector) {
        new WebDriverWait(driver, TIMEOUT)
                .until(elementToBeClickable(selector)).click();
    }

    private void selectCategory(String category) {
        closePopUpAndClick(By.xpath(String.format(".//li/a[@title=\"%s\"]/..", category)));
        logger.info("Category '{}' was selected", category);
    }

    private void selectCity(String cityLabel) {
        new WebDriverWait(driver, TIMEOUT)
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("modal-select-country")))
                .findElement(By.cssSelector(String.format("label[for='%s']", cityLabel)))
                .click();
        new WebDriverWait(driver, TIMEOUT)
                .until(ExpectedConditions.invisibilityOfElementLocated(By.id("modal-select-country")));
        logger.info("City with label {} was choosen", cityLabel);
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Driver was closed");
        }
    }
}
