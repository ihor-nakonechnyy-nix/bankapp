package smoke.smu_2;

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
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

public class SMU_2 {
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
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testATMServices() {

        // фактичний список із UI
        List<String> actualATM = driver.findElements(By.cssSelector("ul.services li:not(.captionone) a"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        // відкидаємо перший елемент "ATM Services"
        //List<String> services = actualATM.subList(1, actualATM.size());

        List<String> expectedATM = Arrays.asList(
                "Withdraw Funds",
                "Transfer Funds",
                "Check Balances",
                "Make Deposits"
        );

        Assert.assertEquals(actualATM, expectedATM, "Список послуг ATM Services не відповідає очікуваному!");

    }
}
