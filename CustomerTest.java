import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class CustomerTest {
    private WebDriver driver;

    @Test
    public void customerTestRun() throws MalformedURLException {

        String sauceUserName = System.getenv("SAUCE_USERNAME");
        String sauceAccessKey = System.getenv("SAUCE_ACCESS_KEY");

        String sauceURL = "https://"+sauceUserName+":"+sauceAccessKey+"@ondemand.eu-central-1.saucelabs.com:443/wd/hub";

        MutableCapabilities sauceOpts = new MutableCapabilities();
        ChromeOptions browserOptions = new ChromeOptions();
        browserOptions.setExperimentalOption("w3c", true);
        browserOptions.setCapability("platformName", "Windows 10");
        browserOptions.setCapability("browserVersion", "93");
        browserOptions.setCapability("sauce:options", sauceOpts);

        driver = new RemoteWebDriver(new URL(sauceURL), browserOptions);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://alpha.realtor.com/?ads=0&ff_disable=true");

        WebElement searchbar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid=\"input-element\"]")));
        searchbar.isEnabled();
        searchbar.sendKeys("San Jose, CA");
        WebElement searchButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[aria-label='Search']")));
        searchButton.click();

        WebElement sortByFilter = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"sortByFilter\"]")));
        sortByFilter.click();
    }

    @AfterEach
    public void cleanUpAfterTestMethod () {
        ((JavascriptExecutor) driver).executeScript("sauce:job-result=" + ("passed"));
        driver.quit();
    }
}