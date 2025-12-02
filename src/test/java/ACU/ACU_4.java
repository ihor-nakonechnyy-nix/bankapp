package ACU;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.BillPayPage;
import pages.HomePage;

import java.time.Duration;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;



public class ACU_4 {

    private WebDriver driver;
    private HomePage homePage;
    private BillPayPage billPayPage;

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
        billPayPage = new BillPayPage(driver);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            // driver.quit();
        }
    }

    @Test
    public void UserCanPayBill() {
        // ---------------- API PART ----------------
        Response responseBeforeSendPayment = RestAssured
                .given()
                .baseUri("http://localhost:8080/parabank/services/bank")
                .basePath("/accounts/13455/")
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .response();

        XmlPath xml1 = new XmlPath(responseBeforeSendPayment.asString());
        double balanceBeforeSendPayment = xml1.getDouble("account.balance");

        System.out.println("BALANCE BEFORE PAYMENT = " +  balanceBeforeSendPayment);

        // ---------------- UI PART ----------------
        homePage.userLogIn("parasoft", "demo");
        homePage.goBillPayLink();

        billPayPage.addPayeeName("John");
        billPayPage.addAddress("1431 Main St");
        billPayPage.addCity("Beverly Hills");
        billPayPage.addState("CA");
        billPayPage.addZipCode("90210");
        billPayPage.addPhone("310-447-4121");
        billPayPage.addAccount("12456");
        billPayPage.addVerifyAccount("12456");
        billPayPage.addAmount("120");
        billPayPage.addFromAccount("13455");
        billPayPage.pressSendPaymentBtn();
        billPayPage.billPaymentCompleteTitleText();

        Assert.assertEquals(billPayPage.billPaymentCompleteTitleText(), "Bill Payment Complete");

        Map<String, String> actualDetails = billPayPage.getBillPaymentConfirmationDetails();

        String expectedPayeeName = "John";
        String expectedAmount = "120.00";
        String expectedAccount = "13455";
        double amountPaid = 120.00;

        Assert.assertEquals(actualDetails.get("PayeeName"), expectedPayeeName, "error");
        Assert.assertEquals(actualDetails.get("Amount"), expectedAmount, "error");
        Assert.assertEquals(actualDetails.get("FromAccount"), expectedAccount, "error");


        // ---------------- API PART ----------------

        Response responseAfterSendPayment = RestAssured
                .given()
                .baseUri("http://localhost:8080/parabank/services/bank")
                .basePath("/accounts/13455/")
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .response();

        XmlPath xml2 = new XmlPath(responseAfterSendPayment.asString());
        double balanceAfterSendPayment = xml2.getDouble("account.balance");

        System.out.println("BALANCE AFTER PAYMENT = " + balanceAfterSendPayment);
        Assert.assertEquals(balanceAfterSendPayment, balanceBeforeSendPayment - amountPaid , "API: Balance invalid!");
    }
}

