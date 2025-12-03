package ADU;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;

import java.time.Duration;

public class ADU_3 {
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

        activityPage.openFirstTransaction();

        TransactionDetails actualTransactionInfo = transactionPage.getFirstTransactionDetails();
        System.out.println("Transaction Details page is displayed" + actualTransactionInfo);

        Assert.assertEquals(actualTransactionInfo.getAmount(), "$100.00", "Сума транзакції не збігається.");
        Assert.assertEquals(actualTransactionInfo.getDescription(), "Funds Transfer Sent", "Опис не збігається.");
        Assert.assertTrue(actualTransactionInfo.getDate().contains("2025"), "Рік у даті неправильний.");
        Assert.assertTrue(actualTransactionInfo.getType().contains("Debit"), "Тип не правильний");
    }
}