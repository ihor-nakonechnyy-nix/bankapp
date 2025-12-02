package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;


import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class BillPayPage {
    private WebDriver driver;

    public BillPayPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @FindBy(xpath = "//input[@name='payee.name']")
    WebElement payeeName;
    @FindBy(xpath = "//input[@name='payee.address.street']")
    WebElement address;
    @FindBy(xpath = "//input[@name='payee.address.city']")
    WebElement city;
    @FindBy(xpath = "//input[@name='payee.address.state']")
    WebElement state;
    @FindBy(xpath = "//input[@name='payee.address.zipCode']")
    WebElement zipCode;
    @FindBy(xpath = "//input[@name='payee.phoneNumber']")
    WebElement phone;
    @FindBy(xpath = "//input[@name='payee.accountNumber']")
    WebElement account;
    @FindBy(xpath = "//input[@name='verifyAccount']")
    WebElement verifyAccount;
    @FindBy(xpath = "//input[@name='amount']")
    WebElement amount;
    @FindBy(xpath = "//select[@name='fromAccountId']")
    WebElement fromAccount;
    @FindBy(xpath = "//input[@value='Send Payment']")
    WebElement sendPaymentBtn;
    @FindBy(xpath = "//h1[text()='Bill Payment Complete']")
    WebElement billPaymentCompleteTitle;
    @FindBy(xpath = "//span[@id='payeeName']")
    WebElement payeeNameResult;
    @FindBy(xpath = "//span[@id='amount']")
    WebElement amountResult;
    @FindBy(xpath = "//span[@id='fromAccountId']")
    WebElement fromAccountIdResult;


    public void addPayeeName(String payeeNameValue) {
        payeeName.sendKeys(payeeNameValue);
    }
    public void addAddress(String addressValue) {
        address.sendKeys(addressValue);
    }
    public void addCity(String cityValue) {
        city.sendKeys(cityValue);
    }
    public void addState(String stateValue) {
        state.sendKeys(stateValue);
    }
    public void addZipCode(String zipCodeValue) {
        zipCode.sendKeys(zipCodeValue);
    }
    public void addPhone(String phoneValue) {
        phone.sendKeys(phoneValue);
    }
    public void addAccount(String accountValue) {
        account.sendKeys(accountValue);
    }
    public void addVerifyAccount(String verifyAccountValue) {
        verifyAccount.sendKeys(verifyAccountValue);
    }
    public void addAmount(String amountValue) {
        amount.sendKeys(amountValue);
    }
    public void addFromAccount(String fromAccountValue) {
        new Select(fromAccount).selectByValue(fromAccountValue);
        fromAccount.sendKeys(fromAccountValue);
    }
    public void pressSendPaymentBtn() {
        sendPaymentBtn.click();
    }
    public String billPaymentCompleteTitleText() {
        return billPaymentCompleteTitle.getText();

    }
    public Map<String, String> getBillPaymentConfirmationDetails() {
        Map<String, String> confirmationDetails = new HashMap<>();

        confirmationDetails.put("PayeeName", payeeNameResult.getText().trim());
        confirmationDetails.put("Amount", amountResult.getText().trim().replace("$",""));
        confirmationDetails.put("FromAccount", fromAccountIdResult.getText().trim());
        return confirmationDetails;
    }

}
