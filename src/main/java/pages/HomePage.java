package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class HomePage {
    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // локатор для посилання на сторінку Accounts Overview
    @FindBy(linkText = "Accounts Overview")
    WebElement accountsOverviewBtn;

    // локатор для посилання на сторінку Open New Account
    @FindBy(linkText = "Open New Account")
    WebElement openNewAccountBtn;


    @FindBy(linkText = "Register")
    public WebElement registrationPageLink;

    @FindBy(linkText = "Request Loan")
    public WebElement requestLoanLink;

    @FindBy(id = "leftPanel")
    private WebElement leftPanel;

    @FindBy(xpath = "//h2[text()='Customer Login']")
    private WebElement customerLoginHeader;

    @FindBy(xpath = "//div[@id='rightPanel']/h1[contains(text(),'Welcome ')]")
    private WebElement rightPanelWelcomeMessage;

    @FindBy(xpath = "//p[@class='smallText']")
    private WebElement LeftPanelWelcomeMessage;

    @FindBy(xpath = "//a[text()='Transfer Funds']")
    private WebElement transferFundsLink;


    // welcome page sections
    @FindBy(xpath = "//*[@class='services']")
    private WebElement servicesOne;
    @FindBy(xpath = "//*[@class='servicestwo']")
    private WebElement servicesTwo;

    // sections lists
    @FindBy(css = "ul.services li:not(.captionone) a")
    private List<WebElement> atmServices;
    @FindBy(css = "ul.servicestwo li:not(.captionone) a")
    private List<WebElement> onlineServices;

    // Latest News section
    @FindBy(xpath = "//h4[text()='Latest News']")
    private WebElement newsSection;

    // LogIn to bank locators
    @FindBy(name = "username")
    private WebElement userLogin;
    @FindBy(name = "password")
    private WebElement userPassword;
    @FindBy(xpath = "//input[@value='Log In']")
    private WebElement logInButton;


    private By latestNewsHeader = By.xpath(".//*[text()='Latest News']");
    private By newsItems = By.cssSelector(".news-item");

    public String getCustomerLoginHeaderText() {
        return customerLoginHeader.getText(); // getText() повертає видимий текст елемента (без HTML).
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

    public List<String> getATMServicesTexts() {
        return atmServices.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
    public List<String> getOnlineServicesTexts() {
        return onlineServices.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
    public void goToRegistration() {
        registrationPageLink.click();
    }
    public void goRequestLoanPage() {
        requestLoanLink.click();
    }
    public void userLogIn(String username, String password) {
        userLogin.sendKeys(username);
        userPassword.sendKeys(password);
        logInButton.click();
    }
    public boolean isNewsSectionDisplayed() {
        return newsSection.isDisplayed();
    }
    public boolean newsSectionContainsTodayDate() {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
        String text = newsSection.getText();
        return text.contains(today);
    }
    public boolean isLatestNewsOverBlueBackground() {
        WebElement header = newsSection.findElement(latestNewsHeader);
        String color = header.getCssValue("background-color");
        return color.contains("rgba(0, 0, 255") || color.contains("blue");
    }
    public long getNewsWithNewLabelCount() {
        List<WebElement> items = newsSection.findElements(newsItems);
        return items.stream()
                .filter(item -> item.getText().contains("New!"))
                .count();
    }

    public String getRightPanelWelcomeText() {
        return rightPanelWelcomeMessage.getText();
    }
    public String getLeftPanelWelcomeText() {
        return LeftPanelWelcomeMessage.getText();
    }
    public void goToAccountsOverview() {
        accountsOverviewBtn.click();
    }
    public void goToOpenNewAccount() {
        openNewAccountBtn.click();
    }
    public void goToTransferFunds() {
        transferFundsLink.click();
    }
}