package ACU;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AccountsOverviewPage;
import pages.HomePage;
import pages.OpenAccountPage;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class ACU_2 {

    private WebDriver driver;
    private HomePage homePage;
    private AccountsOverviewPage accountsOverviewPage;
    private OpenAccountPage openAccountPage;


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
        openAccountPage = new OpenAccountPage(driver);
        accountsOverviewPage = new AccountsOverviewPage(driver);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            // driver.quit();
        }
    }

    @BeforeClass
    public void initializeDB() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/parabank/services/bank/initializeDB"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 204) {
            throw new RuntimeException("DB initialization failed! Status: " + response.statusCode());
        }
    }

    @Test
    public void verifyAdditionalAccountCanBeCreatedForUser() {
        homePage.userLogIn("parasoft", "demo");

        homePage.goToOpenNewAccount();
        openAccountPage.selectAccountType("SAVINGS");
        openAccountPage.clickNewAccountButton();

        final double accountBalance = 1914.76;
        final double newAccountBalance = 100.00;

        String message = openAccountPage.getAccountOpenedMessageText();
        System.out.println("Account Opened message: " + message);
        Assert.assertTrue(message.contains("Account Opened!"));

        openAccountPage.goToAccountsOverview();
        Assert.assertTrue(accountsOverviewPage.isPageOpened());

        double initialAccountAmountToDouble = accountsOverviewPage.getInitialAccountAmountIndex(1);
        double newAccountAmountToDouble = accountsOverviewPage.getInitialAccountAmountIndex(2);

        assertEquals(initialAccountAmountToDouble, accountBalance, "account has minimal amount less");
        assertEquals(newAccountAmountToDouble, newAccountBalance, "account has Minimal amount");

        System.out.println("accountAmount: " + initialAccountAmountToDouble);
        System.out.println("newAccountAmount: " + newAccountAmountToDouble);

    }
}
