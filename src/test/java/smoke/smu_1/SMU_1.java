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
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testCustomerLoginFormIsDisplayed() {
        WebElement leftPanel = driver.findElement(By.id("leftPanel"));
        Assert.assertTrue(leftPanel.isDisplayed(), "Панель leftPanel не відображається");

        WebElement header = driver.findElement(By.xpath("//h2[text()='Customer Login']"));
        String logoText = header.getText();
        Assert.assertEquals(logoText.trim(), "Customer Login", "не відображається");
    }

    @Test
    public void testTwoServicesColumsDisplayed() {
        WebElement services = driver.findElement(By.xpath("//*[@class='services']"));
        Assert.assertTrue(services.isDisplayed(), "the feft service colum is displayed");

        WebElement servicestwo = driver.findElement(By.xpath("//*[@class='servicestwo']"));
        Assert.assertTrue(servicestwo.isDisplayed(), "the right service colum is displayed");
    }
}
