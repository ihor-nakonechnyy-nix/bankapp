package SMU;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SMU_3 {
    private WebDriver driver;
    private HomePage homePage;


    @BeforeMethod
    public void setupDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-features=PasswordChangeDetection,PasswordLeakDetection,AutofillKeyedPasswords");
        options.addArguments("--incognito");
        options.addArguments("--disable-popup-blocking");

        driver = new ChromeDriver(options);
        homePage = new HomePage(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("http://localhost:8080/parabank/index.htm");
    }

    @Test
    public void verifyNewsSection() {
        // 1️⃣ News section is displayed
        Assert.assertTrue(homePage.isNewsSectionDisplayed(), "News section is not displayed!");

        // 2️⃣ Contains today's date
        Assert.assertTrue(homePage.newsSectionContainsTodayDate(), "Today's date is missing in the news section!");

        // 3️⃣ Latest News text has blue background
        Assert.assertTrue(homePage.isLatestNewsOverBlueBackground(),
                "'Latest News' text is not over blue background!");

        // 4️⃣ Two of three news items contain 'New!'
        long count = homePage.getNewsWithNewLabelCount();
        Assert.assertTrue(count >= 2, "Less than 2 news items contain 'New!'");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            //driver.quit();
        }
    }
}
