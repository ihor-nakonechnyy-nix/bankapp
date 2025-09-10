package smoke.smu_1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;


public class SMU_1 {
    private WebDriver driver;

    @BeforeMethod
    public void setupDriver() {
        ChromeOptions options = new ChromeOptions();


        options.addArguments("--start-maximized");
        options.addArguments("--disable-features=PasswordChangeDetection,PasswordLeakDetection,AutofillKeyedPasswords");
        options.addArguments("--incognito");
        options.addArguments("--disable-popup-blocking");

        driver = new ChromeDriver(options);


        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://localhost:8080/parabank/index.htm");
        driver = new ChromeDriver(options);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testCustomerLoginFormIsDisplayed() {
        WebElement header = driver.findElement(By.id("leftPanel"));
        //Assert.assertTrue(header.isDisplayed(), "Customer Login header is not displayed!");
    }


}

