package qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
@Component
public class BlogPage {
    @Autowired
    private WebDriver driver;

    @PostConstruct
    public void init() {
        PageFactory.initElements(driver, this);
    }

    public void addFirstPostToFavorites() {
        WebElement post = driver.findElement(By.cssSelector("div[class='blog-post']"));
        post.findElement(By.cssSelector("div[class='blog__counters-item blog__counters-item_bookmark']")).click();
    }
}
