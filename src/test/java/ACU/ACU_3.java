package ACU;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AccountsOverviewPage;
import pages.HomePage;
import pages.TransferPage;

import java.time.Duration;

public class ACU_3 {

    private WebDriver driver;
    private HomePage homePage;
    private AccountsOverviewPage accountsOverviewPage;
    private TransferPage transferPage;

    String accountFrom = "13455";
    String accountTo = "13566";
    double amountToTransfer = 120.00;

    @BeforeMethod
    public void setDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-features=PasswordChangeDetection,PasswordLeakDetection,AutofillKeyedPasswords");
        options.addArguments("--incognito");
        options.addArguments("--disable-popup-blocking");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.get("http://localhost:8080/parabank/index.htm");

        homePage = new HomePage(driver);
        accountsOverviewPage = new AccountsOverviewPage(driver);
        transferPage = new TransferPage(driver);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            // driver.quit();
        }
    }

    @Test
    public void userCanTransferMoneyBetweenAccounts() {
        homePage.userLogIn("parasoft", "demo");

        String fromBalanceBeforeTransfer = accountsOverviewPage.getAccountFromBalance();
        String toBalanceBeforeTransfer = accountsOverviewPage.getAccountToBalance();
        // BEFORE
        double fromBeforeDouble = Double.parseDouble(fromBalanceBeforeTransfer
                .replace("$", "")
                .replace(",", ""));

        double toBeforeDouble = Double.parseDouble(toBalanceBeforeTransfer
                .replace("$", "")
                .replace(",", ""));


        homePage.goToTransferFunds();

        transferPage.enterAmount(String.valueOf(amountToTransfer));
        transferPage.selectFromAccount(accountFrom);
        transferPage.selectToAccount(accountTo);
        transferPage.pressTransferBtn();

        Assert.assertEquals(transferPage.getTransferCompleteTitleText(),
                "Transfer Complete!");

        homePage.goToAccountsOverview();

        String fromBalanceAfterTransfer = accountsOverviewPage.getAccountFromBalance();
        String toBalanceAfterTransfer = accountsOverviewPage.getAccountToBalance();

            // AFTER
        double fromAfterDouble = Double.parseDouble(fromBalanceAfterTransfer
                .replace("$", "").replace(",", ""));

        double toAfterDouble = Double.parseDouble(toBalanceAfterTransfer
                .replace("$", "").replace(",", ""));

        System.out.println("After from transfer balance: " + fromAfterDouble);
        System.out.println("After to transfer balance: " + toAfterDouble);

        Assert.assertEquals(fromAfterDouble,
                fromBeforeDouble - amountToTransfer, "do not work");

        Assert.assertEquals(toAfterDouble,
                toBeforeDouble + amountToTransfer, "do not work");
    }
}