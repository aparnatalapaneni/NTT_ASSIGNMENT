package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.*;

public class ValidateNewsArticles {

    WebDriver driver;


    @Given("^I am on the ESPN Cricinfo homepage$")
    public void i_am_on_the_ESPN_Cricinfo_homepage() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.espncricinfo.com/");
    }

    @When("^I navigate to the 'News' section$")
    public void i_navigate_to_the_news_section() {
        WebElement newsSection = driver.findElement(By.xpath("//a[text()='News']"));
        newsSection.click();
    }

    @Then("^I should see the latest cricket-related articles$")
    public void i_should_see_the_latest_cricket_related_articles() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href,'story')]")));
        List<WebElement> articles = driver.findElements(By.xpath("//a[contains(@href,'story')]"));
        assertFalse("No articles found in the News section", articles.isEmpty());

        for (int i = 0; i < articles.size(); i++) {
            try {
                WebElement article = articles.get(i);
                scrollToElement(article);
            } catch (StaleElementReferenceException e) {
                articles = driver.findElements(By.xpath("//a[contains(@href,'story')]"));
                if (i < articles.size()) {
                    WebElement article = articles.get(i);
                    scrollToElement(article);
                } else {
                    System.out.println("Index " + i + " is out of bounds after refreshing the list.");
                    break;
                }
            }
        }
    }


    public void scrollToElement (WebElement element) throws InterruptedException {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        Thread.sleep(500);
    }

    // Cleanup after test
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
