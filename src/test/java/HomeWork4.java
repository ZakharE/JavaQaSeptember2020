import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class HomeWork4 {
    private static final Logger logger = LogManager.getLogger(HomePageTest.class);
    private static WebDriver driver;
    private static final DomainConfig cfg = ConfigFactory.create(DomainConfig.class);

    @BeforeTest
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        new ChromeOptions().addArguments("--disable-popup-blocking");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        logger.info("Driver initiated");
    }

    @Test
    public void addTwoItemsToCart() {
        driver.get(cfg.voltMainPage());
        selectCity("city7800000000000");
        selectCategoty("Электроинструменты");
        selectCategoty("Перфораторы");
        waitElementToBeClickable(By.cssSelector("div.producer-list-toggle"));
        String makita = "MAKITA";
        addProductToFilter(makita);
        String zubr = "ЗУБР";
        addProductToFilter(zubr);
        applyFilter();
        sortBy(By.cssSelector("ul[class='select2-results__options']> li:nth-child(1)"));

        String zubrId = addProductToCompareList(zubr);
        String makitaId = addProductToCompareList(makita);
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
    }

    private String addProductToCompareList(String product) {
        WebElement item = driver.findElements(By.xpath(String.format("//ul/li/*[contains(text(), '%s')]/..", product))).get(0);
        String id = item.findElement(By.cssSelector(".new-item-list-name > a")).getAttribute("data-code");
        new WebDriverWait(driver, 10)
                .until(visibilityOf(item.findElement(By.cssSelector("div.new-item-list-compare")))).click();
        By closePopUpCross = By.cssSelector(".ui-button");
        driver.findElement(closePopUpCross).click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(closePopUpCross));
        return id;
    }

    private void sortBy(By sortSelector) {
        waitElementToBeClickable(By.cssSelector("span[class='select2-listing'"));
        driver.findElement(sortSelector).click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("#product-list > li"), 0));
    }

    private void applyFilter() {
        driver.findElement(By.cssSelector("input[value='Подобрать модель']")).click();
    }

    private void addProductToFilter(String product) {
        driver.findElement(By.cssSelector(String.format("input[title='%s']", product))).click();
    }

    private void waitElementToBeClickable(By selector) {
        new WebDriverWait(driver, 10)
                .until(elementToBeClickable(selector)).click();
    }

    private void selectCategoty(String category) {
        new WebDriverWait(driver, 10)
                .until(visibilityOfElementLocated(By.xpath(String.format(".//li/a[@title=\"%s\"]/..", category))))
                .click();
    }

    private void selectCity(String cityLabel) {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("modal-select-country")))
                .findElement(By.cssSelector(String.format("label[for='%s']", cityLabel)))
                .click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(By.id("modal-select-country")));
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Driver was closed");
        }
    }
}
