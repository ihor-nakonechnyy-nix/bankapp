package HomePageSmoke;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import java.time.Duration;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

public class HomePage {
    WebDriver driver;

    // Локатори елементів на сторінці
    @FindBy(id = "leftPanel")
    WebElement leftPanel;

    @FindBy(xpath = "//h2[text()='Customer Login']")
    WebElement customerLoginHeader;

    @FindBy(xpath = "//*[@class='services']")
    WebElement servicesOne;

    @FindBy(xpath = "//*[@class='servicestwo']")
    WebElement servicesTwo;

    @FindBy(xpath = "//h4[text()='Latest News']")
    WebElement newsSection;


    @FindBy(css = "ul.services li:not(.captionone) a")
    private List<WebElement> atmServices;

    @FindBy(css = "ul.servicestwo li:not(.captionone) a")
    private List<WebElement> onlineServices;


    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    // Методи для перевірок
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

    public List<String> getATMServicesTexts() {
        return atmServices.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<String> getOnlineServicesText() {
        return onlineServices.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
}