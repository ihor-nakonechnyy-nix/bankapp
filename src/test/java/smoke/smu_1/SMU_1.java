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
        WebElement servicesOne = driver.findElement(By.xpath("//*[@class='services']"));
        Assert.assertTrue(servicesOne.isDisplayed(), "the feft service colum is displayed");

        WebElement servicesTwo = driver.findElement(By.xpath("//*[@class='servicestwo']"));
        Assert.assertTrue(servicesTwo.isDisplayed(), "the right service colum is displayed");
    }

    @Test
    public void testLatestNewsSectionDisplayed() {
        WebElement newsSection = driver.findElement(By.xpath("//h4[text()='Latest News']"));
        Assert.assertTrue(newsSection.isDisplayed(), "the Latest News displayed");
    }


}