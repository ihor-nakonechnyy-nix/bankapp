package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import java.util.List;
import java.util.stream.Collectors;

public class HomePage {
    private WebDriver driver;

    // Інструктор для Home Page
    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Локатор для Register посилання на сторінку реестрації клієнта
    @FindBy(linkText = "Register")
    public WebElement registrationPageLink;

    //Усі наступні локатори для обєктів на Home Page
    @FindBy(id = "leftPanel")
    private WebElement leftPanel;

    @FindBy(xpath = "//h2[text()='Customer Login']")
    private WebElement customerLoginHeader;

    @FindBy(xpath = "//*[@class='services']")
    private WebElement servicesOne;

    @FindBy(xpath = "//*[@class='servicestwo']")
    private WebElement servicesTwo;

    @FindBy(xpath = "//h4[text()='Latest News']")
    private WebElement newsSection;

    @FindBy(css = "ul.services li:not(.captionone) a")
    private List<WebElement> atmServices;

    @FindBy(css = "ul.servicestwo li:not(.captionone) a")
    private List<WebElement> onlineServices;


    // Методи для перевірок обєктів Home Page
    public boolean isLeftPanelDisplayed() {
        return leftPanel.isDisplayed();
    }

    public String getCustomerLoginHeaderText() {
        return customerLoginHeader.getText();
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
    public void open(String baseUrl) {
        driver.get(baseUrl);
    }

    public void goToRegistration() {
        registrationPageLink.click();
    }
}