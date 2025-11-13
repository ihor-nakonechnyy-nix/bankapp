package RGU;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import pages.HomePage;
import pages.RegistrationPage;

import java.time.Duration;

public class RGU_1 {
    private WebDriver driver;
    private HomePage homePage;
    private RegistrationPage registrationPage;

    @BeforeMethod
    public void setupDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--disable-popup-blocking");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("http://localhost:8080/parabank/index.htm");

        homePage = new HomePage(driver);
        registrationPage = new RegistrationPage(driver);
    }

    @Test
    public void testUserCanBeCreated() {

        // 1. Перейти на сторінку реєстрації
        homePage.goToRegistration();
        registrationPage.waitForPageToLoad();

        // 2. Заповнити форму реєстрації
        String firstName = "Alice";
        String lastName = "Smith";
        String username = "alice" + System.currentTimeMillis(); // унікальний логін
        String password = "demo";

        registrationPage.fillFormForExistUserVerification(
                firstName,
                lastName,
                "123 Main St",
                "Miami",
                "FL",
                "33101",
                "555-1234",
                "123-45-6789",
                username,
                password,
                password
        );

        // 3. Натиснути кнопку REGISTER
        registrationPage.submitForm();

        // --- Перевірка успішної реєстрації ---

        // Повідомлення Welcome Page містить Username
        String welcomeMessage = homePage.getRightPanelWelcomeText();
        Assert.assertTrue(welcomeMessage.contains("Welcome " + username),
                "Welcome " + username);

        // Повідомлення зверху лівого меню містить ім'я та прізвище
        String leftMenuMessage = homePage.getLeftPanelWelcomeText();
        Assert.assertTrue(leftMenuMessage.contains(firstName) && leftMenuMessage.contains(lastName),
                "Left panel should display full name: " + firstName + " " + lastName);

        System.out.println(leftMenuMessage);
        System.out.println(welcomeMessage);
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            //driver.quit();
        }

    }
}