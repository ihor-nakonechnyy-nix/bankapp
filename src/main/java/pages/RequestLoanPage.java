package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RequestLoanPage {
    private WebDriver driver;

    // Конструктор для RequestLoan Page
    public RequestLoanPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    // Локатори для полів форми Apply for a Loan
    private By amountInput = By.id("amount");
    private By downPaymentInput = By.id("downPayment");
    private By ApplyLoanButton = By.xpath("//input[@class='button']");

    // Локатори повідомлень коли Акаунт створено
    private By loanRequestProcessedTitle = By.className("title");
    private By loanProviderName = By.id("loanProviderName");
    private By loanStatus = By.id("loanStatus");
    private By congratsMessage = By.className("ng-scope");
    private By responseDate = By.id("responseDate");
    private By newAccountId = By.xpath("//a[@id='newAccountId' and @class='ng-binding']");

    // Локатор для посилання на сторінку Accounts Overview
    @FindBy(linkText = "Accounts Overview")
    public WebElement accountsOverviewLink;

    // Метод для заповення форми Apply for a Loan
    public void applyForLoan(int loanAmount, int downPayment) {
        driver.findElement(amountInput).sendKeys(String.valueOf(loanAmount));
        driver.findElement(downPaymentInput).sendKeys(String.valueOf(downPayment));

        driver.findElement(ApplyLoanButton).click();
    }

    public String getLoanRequestProcessedTitle() {
        return driver.findElement(loanRequestProcessedTitle).getText();
    }

    public String getNewAccountId() {
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        return driver.findElement(newAccountId).getText();
    }

    public String getLoanProviderName() {
        return driver.findElement(loanProviderName).getText();
    }

    public String getLoanStatus() {
        return driver.findElement(loanStatus).getText();
    }

    public String getCongratsMessage() {
        return driver.findElement(congratsMessage).getText();
    }

    public String getResponseDate() {
        return driver.findElement(responseDate).getText();
    }

    // Періхід на сторінку Accounts Overview
    public void goAccountsOverviewPage() {
        accountsOverviewLink.click();
    }

}