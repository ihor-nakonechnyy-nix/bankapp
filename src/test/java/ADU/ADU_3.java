package ADU;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.HomePage;
import java.time.Duration;
import pages.ActivityPage;
import pages.TransactionPage;

public class ADU_3 {
    private WebDriver driver;
    private HomePage homePage;
    private ActivityPage activityPage;
    private TransactionPage transactionPage;

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
        homePage.userLogIn("ihor", "demo");

         // Open the Account Details, activity page
        driver.findElement(By.cssSelector("a[href='activity.htm?id=13788']")).click();

        // Open the Transaction Details, transaction page
        driver.findElement(By.cssSelector("a[href='transaction.htm?id=14698']")).click();

        // Transaction Details verification
        Assert.assertEquals(transactionPage.getTransactionID(), "14698");
        Assert.assertEquals(transactionPage.getDate(), "10-01-2025");
        Assert.assertEquals(transactionPage.getDescription(), "Down Payment for Loan # 13899");
        Assert.assertEquals(transactionPage.getType(), "Debit");
        Assert.assertEquals(transactionPage.getAmount(), "$20.00");
    }
}