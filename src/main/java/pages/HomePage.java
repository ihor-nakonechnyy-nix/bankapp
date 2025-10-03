package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;


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

public class HomePage {
    private WebDriver driver;

    // Інструктор для Home Page
    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Локатор для посилання на сторінку реестрації
    @FindBy(linkText = "Register")
    public WebElement registrationPageLink;

    // Локатор для посилання на сторінку Request Loan
    @FindBy(linkText = "Request Loan")
    public WebElement requestLoanLink;

    //Локатори для обєктів на Home page

    // Login form
    @FindBy(id = "leftPanel")
    private WebElement leftPanel;

    // Header "Customer Login"
    @FindBy(xpath = "//h2[text()='Customer Login']")
    private WebElement customerLoginHeader;

    // ATM Services panel
    @FindBy(xpath = "//*[@class='services']")
    private WebElement servicesOne;

    // Online Services panel
    @FindBy(xpath = "//*[@class='servicestwo']")
    private WebElement servicesTwo;

    // Latest News section
    @FindBy(xpath = "//h4[text()='Latest News']")
    private WebElement newsSection;

    // ATM Services List
    @FindBy(css = "ul.services li:not(.captionone) a")
    private List<WebElement> atmServices;

    // Online Services list
    @FindBy(css = "ul.servicestwo li:not(.captionone) a")
    private List<WebElement> onlineServices;

    // Локатори для Username та Password та
    @FindBy(name = "username")
    private WebElement userlogin;
    @FindBy(name = "password")
    private WebElement userPassword;
    @FindBy(css = "input.button[type='submit']")
    private WebElement logInButton;


    // Методи для перевірок обєктів Home Page

    public String getCustomerLoginHeaderText() {
        return customerLoginHeader.getText();
    }

    public boolean isLeftPanelDisplayed() {
        return leftPanel.isDisplayed();
    }

    public boolean isServicesOneDisplayed() {
        return servicesOne.isDisplayed();
    }

    public boolean isServicesTwoDisplayed() {
        return servicesTwo.isDisplayed();
    }

    public boolean isLatestNewsSectionDisplayed() {
        return newsSection.isDisplayed();
    }

    // Перевірка списку в блоці ATM Services (Home Page)
    public List<String> getATMServicesTexts() {
        return atmServices.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    // Перевірка списку в блоці Online Services (Home Page)
    public List<String> getOnlineServicesTexts() {
        return onlineServices.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    // Періхід на сторінку реєстрації клієнта
    public void goToRegistration() {
        registrationPageLink.click();
    }

    // Періхід на сторінку реєстрації клієнта
    public void goRequestLoanPage() {
        requestLoanLink.click();
    }


    public void userLogIn() {
        userlogin.sendKeys("ihor");
        userPassword.sendKeys("demo");
        logInButton.click();
    }
}