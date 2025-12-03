package ADU;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;

import java.time.Duration;

public class ADU_1 {
    private WebDriver driver;
    private HomePage homePage;
    private ActivityPage activityPage;
    private TransactionPage transactionPage;
    private AccountsOverviewPage accountsOverviewPage;

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

        homePage = new HomePage(driver);
        activityPage = new ActivityPage(driver);
        transactionPage = new TransactionPage(driver);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            //driver.quit();
        }
    }

    @Test
    public void transactionDetailsTest() {
        homePage.userLogIn("parasoft", "demo");
        homePage.openFirstAccount();

        Assert.assertTrue(activityPage.accountIDIsDisplayed(), "Account Number is not displayed");
        Assert.assertTrue(activityPage.accountTypeIsDisplayed(), "Account Type   is not displayed");
        Assert.assertTrue(activityPage.balanceIsDisplayed(), "Balance is not displayed");
        Assert.assertTrue(activityPage.availableBalanceIsDisplayed(), "Available Balance is not displayed");

        Assert.assertEquals(activityPage.isAllSelectedByDefaultForActivityPeriodFilter(), "All",
                "Default selected period is not 'All'");

        Assert.assertEquals(activityPage.isAllSelectedByDefaultForTransactionTypeFilter(), "All",
                "Default selected period is not 'All'");
    }
}

