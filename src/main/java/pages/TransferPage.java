package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TransferPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public TransferPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @FindBy(xpath = "//input[@id='amount']")
    WebElement transferAmount;
    @FindBy(xpath = "//select[@id='fromAccountId']")
    WebElement fromAccountId;
    @FindBy(xpath = "//select[@id='toAccountId']")
    WebElement toAccountId;
    @FindBy(xpath = "//input[@value='Transfer']")
    WebElement transferBtn;
    @FindBy(xpath = "//h1[@class='title' and text()='Transfer Complete!']")
    WebElement transferCompleteTitle;

    public void enterAmount(String amount) {
        transferAmount.clear();
        transferAmount.sendKeys(amount);
    }

    public void selectFromAccount(String accountFrom) {
        new Select(fromAccountId).selectByValue(accountFrom);

    }

    public void selectToAccount(String accountTo) {
        new Select(toAccountId).selectByValue(accountTo);

    }

    public void pressTransferBtn() {
        transferBtn.click();
    }

    public String getTransferCompleteTitleText() {
        return transferCompleteTitle.getText();

    }
}