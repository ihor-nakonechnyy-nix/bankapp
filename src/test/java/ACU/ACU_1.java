    package ACU;

    import org.openqa.selenium.WebDriver;
    import org.openqa.selenium.chrome.ChromeDriver;
    import org.openqa.selenium.chrome.ChromeOptions;
    import org.testng.Assert;
    import org.testng.annotations.AfterMethod;
    import org.testng.annotations.BeforeMethod;
    import org.testng.annotations.Test;
    import pages.AccountsOverviewPage;
    import pages.HomePage;

    import java.time.Duration;

    public class ACU_1 {

        private WebDriver driver;
        private HomePage homePage;
        private AccountsOverviewPage accountsOverviewPage;

        // Очікувані значення, які були встановлені в Admin
        private final String EXPECTED_BALANCE = "2014.76";
        private final String EXPECTED_AVAILABLE = "2014.76";
        private final String EXPECTED_TOTAL = "2014.76";

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

            // створюємо Pages Objects
            homePage = new HomePage(driver);
            accountsOverviewPage = new AccountsOverviewPage(driver);
        }

        @AfterMethod
        public void tearDown() {
            if (driver != null) {
                //driver.quit();
            }
        }

        @Test
        public void verifyAccountCreatedWithAdminDefaultValues() {


            // Login
            homePage.userLogIn("parasoft", "demo");

            // Перехід до Accounts Overview
            homePage.goToAccountsOverview();

            // Ініціалізуємо сторінку тільки після переходу!
            accountsOverviewPage = new AccountsOverviewPage(driver);

            // 2️ Accounts Overview page is displayed
            Assert.assertTrue(accountsOverviewPage.isPageOpened(),
                    "Accounts Overview page is not displayed");

            // 3️ Validate account values
            Assert.assertEquals(accountsOverviewPage.getBalance(), EXPECTED_BALANCE,
                    "Balance is incorrect");

            Assert.assertEquals(accountsOverviewPage.getAvailableAmount(), EXPECTED_AVAILABLE,
                    "Available Amount is incorrect");

            Assert.assertEquals(accountsOverviewPage.getTotal(), EXPECTED_TOTAL,
                    "Total value is incorrect");
        }
    }
