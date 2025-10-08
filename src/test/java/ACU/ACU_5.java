package ACU;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.RequestLoanPage;
import pages.HomePage;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ACU_5 {

    private WebDriver driver;
    private HomePage homePage;
    private RequestLoanPage requestLoanPage;

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

        // створюємо Pages Objects
        homePage = new HomePage(driver);
        requestLoanPage = new RequestLoanPage(driver);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            //driver.quit();
        }
    }

    @Test
    public void testApplyLoan() {
        homePage.userLogIn("ihor", "demo");
        homePage.goRequestLoanPage();
        //requestLoanPage.applyForLoan(100, 20);
//        requestLoanPage.getLoanRequestProcessedTitle();
//        requestLoanPage.getLoanProviderName();
//        requestLoanPage.getLoanStatus();
//        requestLoanPage.getCongratsMessage();
//        requestLoanPage.getResponseDate();
        String newAccountId = requestLoanPage.getNewAccountId();

        // отримуємо дату зі сторінки
        String actualDateStr = requestLoanPage.getResponseDate();
        // парсимо отриману дату
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-d-yyyy");
        LocalDate actualDate = LocalDate.parse(actualDateStr, formatter);
        // сьогоднішня дата
        LocalDate today = LocalDate.now();
        Assert.assertEquals(actualDate, today, "Дата не співпадає з сьогоднішньою!");

        // переходимо на сторінку Accounts Overview
        requestLoanPage.goAccountsOverviewPage();

        WebElement accountNumber = driver.findElement(By.partialLinkText(newAccountId));
        Assert.assertTrue(accountNumber.isDisplayed());

    }
}
