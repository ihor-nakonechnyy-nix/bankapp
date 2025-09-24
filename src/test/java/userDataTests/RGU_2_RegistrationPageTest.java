package userDataTests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;

import pages.HomePage;
import pages.RegistrationPage;

import java.time.Duration;

public class RGU_2_RegistrationPageTest {
    private WebDriver driver;
    private HomePage homePage;
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
        driver.get("http://localhost:8080/parabank/index.htm");

        homePage = new HomePage(driver);
        registrationPage = new RegistrationPage(driver);

    }

    @Test
    public void testUserRegistration() {
        homePage.registrationPageLink.click();

        // Переходимо на сторінку реєстрації
        homePage.goToRegistration();

        // Заповнюємо форму реєстрації
        registrationPage.fillForm(
                "john",
                "Dou",
                "Marina Bay",
                "Miami",
                "FL",
                "7007",
                "333-555-777",
                "111-888",
                "john",
                "demo",
                "demo");

        // Відправляємо форму
        registrationPage.submitForm();
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            //driver.quit(); // Закриваємо браузер
        }

        String actualError = registrationPage.getErrorMessageText();
        Assert.assertEquals(actualError, "Please enter a username and password.");
    }
}





