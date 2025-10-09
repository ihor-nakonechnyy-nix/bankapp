package pages;

import org.openqa.selenium.WebDriver; // WebDriver — інтерфейс Selenium, який керує браузером (запуск, навігація, т.д.). У класі зберігається щоб виконувати дії
import org.openqa.selenium.WebElement; // інтерфейс, що представляє HTML-елемент сторінки (input, a, div тощо).
import org.openqa.selenium.support.FindBy; // анотація Page Object Model (POM) для позначення локаторів полів у класі.
import org.openqa.selenium.support.PageFactory; // утиліта Selenium, яка ініціалізує поля з @FindBy (створює проксі-об'єкти, котрі шукають елементи при першому зверненні).

import java.util.List; // стандартний java.util.List для списків елементів.
import java.util.stream.Collectors; // утиліта для роботи зі Stream API (тут використовується для збирання текстів елементів в List<String>).


/* Тести що потрібно зробити
UI:
RGU-2
RGU-3
ACU-5
ADU-3

API:
RGA-2
ACA-1
ACA-2
*/

// Оголошення класу і поле driver
public class HomePage { // POM-клас, що представляє сторінку Home.
    private WebDriver driver; // екземпляр драйвера, що використовується усіма методами цього Page Object.
    // private — інкапсуляція: інші класи не бачать прямий доступ до драйвера через це поле.

    // Конструктор приймає driver. PageFactory.initElements(driver, this) ініціалізує всі елементи з анотаціями @FindBy.
    // Конструктор приймає вже створений WebDriver (звичайно тестовий код створює драйвер і передає у Page Object).
    public HomePage(WebDriver driver) {
        this.driver = driver; // зберігаємо драйвер у полі класу.
        PageFactory.initElements(driver, this); // Ініціалізує усі поля з анотаціями @FindBy.

        /*Як працює (суть):
        створює проксі-об'єкти для WebElement полів —
        фактичний пошук елементів в DOM відбудеться не під час ініціалізації конструктора,
        а при першому зверненні до поля (lazy lookup).*/
    }

    // Локатори (поля з @FindBy)

    // Локатор шукає <a> (або інший посилання) з видимим текстом точно "Register".
    // Шукає елемент за видимим текстом посилання. Чутливий до точного тексту.
    @FindBy(linkText = "Register")
    public WebElement registrationPageLink; // public — елемент відкритий для інших класів.

    // Аналогічно: посилання з текстом "Request Loan".
    @FindBy(linkText = "Request Loan")
    public WebElement requestLoanLink;

    // Шукає елемент за id="leftPanel". Це контейнер логіну.
    // Шукає по id (швидко і надійно якщо id стабільний).
    @FindBy(id = "leftPanel")
    private WebElement leftPanel;

    // Xpath знаходить h2, текст якого точно дорівнює "Customer Login".
    @FindBy(xpath = "//h2[text()='Customer Login']")
    private WebElement customerLoginHeader;

    // Xpath шукає будь-який елемент (*) у якого атрибут class точно рівний 'services'
    // дуже гнучкий, але більш повільний і часто крихкий (чутливий до тексту і структури DOM).
    @FindBy(xpath = "//*[@class='services']")
    private WebElement servicesOne;

    // Аналогічно для другого блоку: Online Services panel
    @FindBy(xpath = "//*[@class='servicestwo']")
    private WebElement servicesTwo;

    // Заголовок Latest News — з тією ж приміткою про точний текст.
    @FindBy(xpath = "//h4[text()='Latest News']")
    private WebElement newsSection;

    // CSS-селектор: знайди ul з класом services, в ньому всі li, які не мають класу captionone (li:not(.captionone)), і з цих li візьми елементи a (посилання).
    // компактний і швидкий, добре підтримує класи (.classname) і псевдокласи (:not(...)).
    @FindBy(css = "ul.services li:not(.captionone) a")
    private List<WebElement> atmServices; // Повертається List<WebElement> — колекція усіх знайдених елементів

    // Те саме для другого списку: Online Services list
    @FindBy(css = "ul.servicestwo li:not(.captionone) a")
    private List<WebElement> onlineServices;

    // Локатори полів логіну: знаходяться за атрибутами name (username/password) і кнопка — input з класом button і типом submit.
    // Шукає по name-атрибуту (звично для input)
    @FindBy(name = "username")
    private WebElement userLogin;
    @FindBy(name = "password")
    private WebElement userPassword;
    // Локатор для кнопки Register
    @FindBy(css = "input.button[type='submit']")
    private WebElement logInButton;

    // Методи (поведінка сторінки)
    public String getCustomerLoginHeaderText() {
        return customerLoginHeader.getText(); // getText() повертає видимий текст елемента (без HTML).
    }

    public boolean isLeftPanelDisplayed() {
        return leftPanel.isDisplayed(); // isDisplayed() повертає true якщо елемент у DOM і видимий для користувача.
    }

    // Аналогічні перевірки видимості для різних секцій. Знову: краще чекати видимість явно, ніж орієнтуватися на миттєвий виклик.
    public boolean isServicesOneDisplayed() {
        return servicesOne.isDisplayed();
    }

    public boolean isServicesTwoDisplayed() {
        return servicesTwo.isDisplayed();
    }

    public boolean isLatestNewsSectionDisplayed() {
        return newsSection.isDisplayed();
    }

    /*Тут відбувається:
    Беремо список atmServices (WebElement).
    Через Stream API викликаємо для кожного елемента getText() (скорочено WebElement::getText — method reference).
    Збираємо у List<String> (список текстів посилань).*/
    public List<String> getATMServicesTexts() {
        return atmServices.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    // Те ж саме для онлайн-сервісів.
    public List<String> getOnlineServicesTexts() {
        return onlineServices.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
    /*Зауваження:
    Якщо списку ще немає (DOM не готовий), може повернутися порожній список або виклик кине виняток
    (залежить від того як PageFactory проксі обробляє findElements). Частіше — порожній список.
    Рекомендується trim() текстів якщо очікуються зайві пробіли: .map(e -> e.getText().trim()).
    Якщо елементи динамічно з’являються, краще використовувати
    WebDriverWait + visibilityOfAllElementsLocatedBy.*/

    // Натискає посилання "Register" → навігація на іншу сторінку.
    public void goToRegistration() {
        registrationPageLink.click();
    }

    // Клік по Request Loan. Перехід по посиланню на сторінку Request Loan
    public void goRequestLoanPage() {
        requestLoanLink.click();
    }


    // Метод автоматично вводить логін/пароль
    public void userLogIn(String username, String password) {
        userLogin.sendKeys(username);
        userPassword.sendKeys(password);
        logInButton.click();

    }
}