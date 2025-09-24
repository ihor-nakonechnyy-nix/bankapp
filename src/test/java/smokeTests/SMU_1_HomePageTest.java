package smokeTests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;

import java.time.Duration;

public class SMU_1_HomePageTest {
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

        homePage = new HomePage(driver); // створюємо Page Object

    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testCustomerLoginFormIsDisplayed() {
        Assert.assertTrue(homePage.isLeftPanelDisplayed(), "Панель leftPanel не відображається");
    }

    @Test
    public void testCustomerLoginHeaderGetText() {
        Assert.assertEquals(homePage.getCustomerLoginHeaderText(), "Customer Login", "Заголовок не відображається");
    }

    @Test
    public void testTwoServicesColumsDisplayed() {
        Assert.assertTrue(homePage.isServicesOneDisplayed(), "the left service colum is NOT displayed");
        Assert.assertTrue(homePage.isServicesTwoDisplayed(), "the right service colum is NOT displayed");
    }

    @Test
    public void testNewsSectionDisplayed() {
        Assert.assertTrue(homePage.isLatestNewsSectionDisplayed(), "the News Section is NOT displayed");
    }
}