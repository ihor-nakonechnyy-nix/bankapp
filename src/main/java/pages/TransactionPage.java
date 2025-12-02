package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TransactionPage {
    private WebDriver driver;

    // Конструктор для ActivityPage Page
    public TransactionPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }


    @FindBy(xpath = "//td[normalize-space()='Transaction ID:']/following-sibling::td")
    WebElement transactionID;

    @FindBy(xpath = "//td[normalize-space()='Date:']/following-sibling::td")
    WebElement date;

    @FindBy(xpath = "//td[normalize-space()='Description:']/following-sibling::td")
    WebElement description;

    @FindBy(xpath = "//td[normalize-space()='Type:']/following-sibling::td")
    WebElement type;

    @FindBy(xpath = "//td[normalize-space()='Amount:']/following-sibling::td")
    WebElement amount;

    public TransactionDetails getFirstTransactionDetails() {
        String transId = transactionID.getText().trim();
        String transDate = date.getText().trim();
        String transDescription = description.getText().trim();
        String transType = type.getText().trim();
        String transAmount = amount.getText().trim();

        return new TransactionDetails(transId, transDate, transDescription, transType, transAmount);

    }
}

