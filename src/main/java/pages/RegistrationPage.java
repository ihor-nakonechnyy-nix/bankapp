package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;

public class RegistrationPage {
    private WebDriver driver;

    // Конструктор для Registration Page
    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    // Локатори для полів форми реєстрації
    private By firstNameInput = By.id("customer.firstName");
    private By lastNameInput = By.id("customer.lastName");
    private By streetInput = By.id("customer.address.street");
    private By cityInput = By.id("customer.address.city");
    private By stateInput = By.id("customer.address.state");
    private By zipCodeInput = By.id("customer.address.zipCode");
    private By phoneNumberInput = By.id("customer.phoneNumber");
    private By ssnInput = By.id("customer.ssn");
    private By usernameInput = By.id("customer.username");
    private By passwordInput = By.id("customer.password");
    private By repeatedPasswordInput = By.id("repeatedPassword");

    // Локатор для кнопки реєстрації (submit)
    private By submitButton = By.xpath("//input[@value='Register']");

    // Локатори повідомленню про помилки
    private By customerUsernameError = By.id("customer.username.errors");
    private By PasswordError = By.id("customer.password.errors");
    private By repeatedPasswordError = By.id("repeatedPassword.errors");


    // Метод для заповення форми реєстрації
    public void fillFormForExistUserVerification(String firstName, String lastName, String street,
                                                 String city, String state, String zipCode,
                                                 String phoneNumber, String ssn,
                                                 String username, String password, String repeatedPassword) {

        driver.findElement(firstNameInput).sendKeys(firstName);
        driver.findElement(lastNameInput).sendKeys(lastName);
        driver.findElement(streetInput).sendKeys(street);
        driver.findElement(cityInput).sendKeys(city);
        driver.findElement(stateInput).sendKeys(state);
        driver.findElement(zipCodeInput).sendKeys(zipCode);
        driver.findElement(phoneNumberInput).sendKeys(phoneNumber);
        driver.findElement(ssnInput).sendKeys(ssn);
        driver.findElement(usernameInput).sendKeys(username);
        driver.findElement(passwordInput).sendKeys(password);
        driver.findElement(repeatedPasswordInput).sendKeys(repeatedPassword);
    }

    // Метод для заповення форми реєстрації
    public void fillFormForPasswordVerification(String firstName, String lastName, String street,
                                                String city, String state, String zipCode,
                                                String phoneNumber, String ssn,
                                                String username) {

        driver.findElement(firstNameInput).sendKeys(firstName);
        driver.findElement(lastNameInput).sendKeys(lastName);
        driver.findElement(streetInput).sendKeys(street);
        driver.findElement(cityInput).sendKeys(city);
        driver.findElement(stateInput).sendKeys(state);
        driver.findElement(zipCodeInput).sendKeys(zipCode);
        driver.findElement(phoneNumberInput).sendKeys(phoneNumber);
        driver.findElement(ssnInput).sendKeys(ssn);
        driver.findElement(usernameInput).sendKeys(username);

    }

    // Метод для натискання кнопки submit
    public void submitForm() {
        driver.findElement(submitButton).click();
    }

    // Методи для оотримання повідомлень про помилки
    public String getUserErrorMessageText() {
        return driver.findElement(customerUsernameError).getText();
    }

    public String getPasswordErrorMessageText() {
        return driver.findElement(PasswordError).getText();
    }

    public String getRepeatPasswordErrorMessageText() {
        return driver.findElement(repeatedPasswordError).getText();
    }
}
