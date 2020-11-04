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
        driver.get(cfg.hostname());
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("modal-select-country")))
                .findElement(By.cssSelector("label[for='city7800000000000']"))
                .click();

        new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(By.id("modal-select-country")));

        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(By.xpath(".//li/a[@title=\"Электроинструменты\"]/..")))
                .click();


        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(By.xpath(".//li/a[@title=\"Перфораторы\"]/..")))
                .click();

        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.producer-list-toggle"))).click();
        driver.findElement(By.cssSelector("input[title='MAKITA']")).click();
        driver.findElement(By.cssSelector("input[title='ЗУБР']")).click();
        driver.findElement(By.cssSelector("input[value='Подобрать модель']")).click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.cssSelector("span[class='select2-listing'"))).click();
        driver.findElement(By.cssSelector("ul[class='select2-results__options']> li:nth-child(1)")).click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//ul/li/*[contains(text(), 'ЗУБР')]/.."), 0));
        WebElement item = driver.findElements(By.xpath("//ul/li/*[contains(text(), 'ЗУБР')]/..")).get(0);
        String zubr = item.findElement(By.cssSelector(".new-item-list-name > a")).getAttribute("data-code");

        item.findElement(By.cssSelector("div.new-item-list-compare")).click();
        driver.findElement(By.cssSelector(".ui-button")).click();
        item = driver.findElements(By.xpath("//ul/li/*[contains(text(), 'MAKITA')]/..")).get(0);
        item.findElement(By.cssSelector("div.new-item-list-compare")).click();
        String makita = item.findElement(By.tagName(".new-item-list-name > a")).getAttribute("data-code");
        driver.findElement(By.cssSelector(".ui-button")).click();
        driver.findElement(By.id("cCountCompare")).click();
        List<WebElement> elements = driver.findElements(By.id(makita + "_compare"));
        List<WebElement> elements1 = driver.findElements(By.id(zubr + "_compare"));
        assert elements.size() == 1;
        assert elements1.size() == 1;

    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Driver was closed");
        }
    }
}
