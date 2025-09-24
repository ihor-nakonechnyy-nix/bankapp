package smokeTests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.HomePage;

import java.time.Duration;
import java.util.List;
import java.util.Arrays;

public class SMU_2_HomePageTest {
    private WebDriver driver;
    private HomePage homePage;


    @BeforeMethod
    public void setDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-features=PasswordChangeDetection,PasswordLeakDetection,AutofillKeyedPasswords");
        options.addArguments("--incognito");
        options.addArguments("--disable-popup-blocking");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        driver.get("http://localhost:8080/parabank/index.htm");

        // ініціалізуємо Page Object
        homePage = new HomePage(driver);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testATMServices() {
        List<String> expectedATM = Arrays.asList(
                "Withdraw Funds",
                "Transfer Funds",
                "Check Balances",
                "Make Deposits"
        );

        Assert.assertEquals(homePage.getATMServicesTexts(),
                expectedATM,
                "Список послуг ATM Services не відповідає очікуваному!");
    }

    @Test
    public void testOnlineServices() {
        List<String> expectedOnlineServices = Arrays.asList(
                "Bill Pay",
                "Account History",
                "Transfer Funds"
        );

        Assert.assertEquals(homePage.getOnlineServicesTexts(),
                expectedOnlineServices,
                "Список послуг Online Services не відповідає очікуваному!");

    }
}