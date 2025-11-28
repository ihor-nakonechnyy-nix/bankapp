package RGU;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.RegistrationPage;

import java.time.Duration;

public class RGU_3 {
    private WebDriver driver;
    private RegistrationPage registrationPage;

    @BeforeMethod
    public void setupDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-features=PasswordChangeDetection,PasswordLeakDetection,AutofillKeyedPasswords");
        options.addArguments("--incognito");
        options.addArguments("--disable-popup-blocking");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        driver.get("http://localhost:8080/parabank/register.htm");

        registrationPage = new RegistrationPage(driver);

    }

    @Test
    public void testUserRegistration2() {

        // Заповнюємо форму реєстрації
        registrationPage.fillFormForPasswordVerification(
                "john",
                "Dou",
                "Marina Bay",
                "Miami",
                "FL",
                "7007",
                "333-555-777",
                "111-888",
                "john");

        // Відправляємо форму
        registrationPage.submitForm();

        String PasswordErrorMessageTest = registrationPage.getPasswordErrorMessageText();
        Assert.assertEquals(PasswordErrorMessageTest, "Password is required.");

        String repeatedPasswordErrorTest = registrationPage.getRepeatPasswordErrorMessageText();
        Assert.assertEquals(repeatedPasswordErrorTest, "Password confirmation is required.");
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            //driver.quit(); // Закриваємо браузер
        }
    }
}





