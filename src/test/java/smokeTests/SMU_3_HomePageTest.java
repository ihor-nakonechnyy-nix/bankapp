package smokeTests;

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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class SMU_3_HomePageTest {
    private WebDriver driver;

    @BeforeMethod
    public void setupDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-features=PasswordChangeDetection,PasswordLeakDetection,AutofillKeyedPasswords");
        options.addArguments("--incognito");
        options.addArguments("--disable-popup-blocking");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        driver.get("http://localhost:8080/parabank/index.htm");


//        Це код для логування на банк
//        WebElement inputName = driver.findElement(By.xpath("//div[@class='login']//input[@name='username']"));
//        inputName.sendKeys("ihor");
//        WebElement pass = driver.findElement(By.xpath("//div[@class='login']//input[@name='password']"));
//        pass.sendKeys("947647");
//        driver.findElement(By.xpath("//input[@value='Log In']")).click();

        //почитати про Page factory
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testDateSection() {

        LocalDate today = LocalDate.now().minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy"); // формат може змінюватися
        String todayFormatted = today.format(formatter);

        // 2. Знаходимо секцію на сторінці
        WebElement dateSection = driver.findElement(By.className("captionthree")); // або By.cssSelector/By.xpath

        // 3. Перевірка, що текст секції містить сьогоднішню дату
        String sectionText = dateSection.getText();
        Assert.assertTrue(sectionText.contains(todayFormatted),
                "Секція не містить сьогоднішню дату: " + todayFormatted);


    }
}
