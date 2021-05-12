package tqs.airquality.functional;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.platform.engine.Cucumber;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


@Cucumber
public class AirQualitySearch {

    private WebDriver driver;

    private int numberHits = 0;
    private int numberMisses = 0;
    private int numberRequests = 0;

    @When("I navigate to {string}")
    public void navigateTo(String url) {
        System.setProperty("webdriver.gecko.driver", "/Users/mario/Downloads/geckodriver");
        driver = new FirefoxDriver();
        driver.get(url);
    }

    @And("I type the name of the city {string}")
    public void typeAirQualityCity(String city){
        driver.findElement(By.id("city")).sendKeys(city);
    }

    @And("I type the name of the country {string}")
    public void typeAirQualityCountry(String country){
        driver.findElement(By.id("country")).sendKeys(country);
    }

    @And("I press Search")
    public void pressSearchAirQuality() {
        driver.findElement(By.id("search_air_quality")).click();
    }

    @And("I change tab to Cities and Statistics")
    public void changeTab() {
        driver.findElement(By.linkText("Cities and Statistics")).click();
    }

    @And("I check Cache Statistics")
    public void storeCacheStatics() {
        numberHits = Integer.parseInt(driver.findElement(By.cssSelector(".MuiBox-root-152")).getText());
        numberMisses = Integer.parseInt(driver.findElement(By.cssSelector(".MuiBox-root-157")).getText());
        numberRequests = Integer.parseInt(driver.findElement(By.cssSelector(".MuiBox-root-167")).getText());
    }

    @And("I press the city card of {string}")
    public void pressCityCard(String city) {
        driver.findElement(By.id("card_"+city)).click();
        {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.textToBe(By.cssSelector(".MuiTypography-h1:nth-child(2)"), city));
        }
    }

    @And("I close Air Details modal")
    public void closeModal() {
        driver.findElement(By.linkText("X")).click();
    }

    @Then("I check if Cache Statistics changed")
    public void checkChangeCacheStatistics() {
        int curNumberHits = Integer.parseInt(driver.findElement(By.cssSelector(".MuiBox-root-152")).getText());
        int curNumberMisses = Integer.parseInt(driver.findElement(By.cssSelector(".MuiBox-root-157")).getText());
        int curNumberRequests = Integer.parseInt(driver.findElement(By.cssSelector(".MuiBox-root-167")).getText());

        assertThat((curNumberHits==numberHits && curNumberMisses==numberMisses+1) ||
                        (curNumberHits==numberHits + 1 && curNumberMisses==numberMisses), is(true));
        assertThat(curNumberRequests==numberRequests+1, is(true));

        numberHits = curNumberHits;
        numberMisses = curNumberMisses;
        numberRequests = curNumberRequests;
    }

    @Then("I check if number of cache hits increased")
    public void checkCacheHitsIncreased() {
        int curNumberHits = Integer.parseInt(driver.findElement(By.cssSelector(".MuiBox-root-152")).getText());

        assertThat(curNumberHits==numberHits+1, is(true));

        numberHits = curNumberHits;
    }

    @Then("I see Air Details of {string}")
    public void seeAirDetails(String location) {
        {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.textToBe(By.id("location"), location));
        }
        assertThat(driver.findElement(By.cssSelector(".MuiBox-root-148")).getText(),
                is("pm10"));
        assertThat(driver.findElement(By.cssSelector(".MuiBox-root-151")).getText(),
                is("Mold Level"));
        assertThat(driver.findElement(By.cssSelector(".MuiBox-root-154")).getText(),
                is("Pollen Level Tree"));
        assertThat(driver.findElement(By.cssSelector(".MuiBox-root-160")).getText(),
                is("Pollen Level Grass"));
        assertThat(driver.findElement(By.cssSelector(".MuiBox-root-163")).getText(),
                is("Predominant Pollen Type"));
        assertThat(driver.findElement(By.cssSelector(".MuiBox-root-128")).getText(),
                is("aqi"));
        assertThat(driver.findElement(By.cssSelector(".MuiBox-root-131")).getText(),
                is("so2"));
        assertThat(driver.findElement(By.cssSelector(".MuiBox-root-134")).getText(),
                is("no2"));
        assertThat(driver.findElement(By.cssSelector(".MuiBox-root-137")).getText(),
                is("pm25"));
        assertThat(driver.findElement(By.cssSelector(".MuiBox-root-140")).getText(),
                is("o3"));
        assertThat(driver.findElement(By.cssSelector(".MuiBox-root-143")).getText(),
                is("co"));
    }

    @Then("I see a modal with Air Details of {string}")
    public void seeModalAirDetails(String city) {
        assertThat(driver.findElement(By.cssSelector(".MuiTypography-h1:nth-child(2)")).getText(), is(city));
        assertThat(driver.findElement(By.cssSelector(".MuiBox-root-216")).getText(), is("pm10"));
        assertThat(driver.findElement(By.cssSelector(".MuiBox-root-219")).getText(), is("Mold Level"));
        assertThat(driver.findElement(By.cssSelector(".MuiBox-root-222")).getText(), is("Pollen Level Tree"));
        assertThat(driver.findElement(By.cssSelector(".MuiBox-root-225")).getText(), is("Pollen Level Weed"));
        assertThat(driver.findElement(By.cssSelector(".MuiBox-root-228")).getText(), is("Pollen Level Grass"));
        assertThat(driver.findElement(By.cssSelector(".MuiBox-root-231")).getText(), is("Predominant Pollen Type"));
        assertThat(driver.findElement(By.cssSelector(".MuiBox-root-196")).getText(), is("aqi"));
        assertThat(driver.findElement(By.cssSelector(".MuiBox-root-199")).getText(), is("so2"));
        assertThat(driver.findElement(By.cssSelector(".MuiBox-root-202")).getText(), is("no2"));
        assertThat(driver.findElement(By.cssSelector(".MuiBox-root-205")).getText(), is("pm25"));
        assertThat(driver.findElement(By.cssSelector(".MuiBox-root-208")).getText(), is("o3"));
        assertThat(driver.findElement(By.cssSelector(".MuiBox-root-211")).getText(), is("co"));
    }
}
