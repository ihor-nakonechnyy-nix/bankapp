package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;


import java.time.Duration;

public class ActivityPage {
    private WebDriver driver;

    public ActivityPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @FindBy(id = "accountId")
    WebElement accountID;
    @FindBy(id = "accountType")
    WebElement accountType;
    @FindBy(id = "balance")
    WebElement balance;
    @FindBy(id = "availableBalance")
    WebElement availableBalance;
    @FindBy(id = "month")
    WebElement ActivityPeriodFilter;
    @FindBy(id = "transactionType")
    WebElement transactionTypeFilter;



    @FindBy(xpath = "//table[@id='transactionTable']//tr[1]/td[2]")
    WebElement transactionTableFirstTransaction;

    public boolean accountIDIsDisplayed() {
        accountID.isDisplayed();
        return true;

    }
    public boolean accountTypeIsDisplayed() {
        accountType.isDisplayed();
        return true;
    }
    public boolean balanceIsDisplayed() {
        balance.isDisplayed();
        return true;
    }
    public boolean availableBalanceIsDisplayed() {
        availableBalance.isDisplayed();
        return true;
    }
    public String isAllSelectedByDefaultForActivityPeriodFilter() {
        Select select = new Select(ActivityPeriodFilter);
        return select.getFirstSelectedOption().getText().trim();

    }
    public String isAllSelectedByDefaultForTransactionTypeFilter() {
        Select select = new Select(transactionTypeFilter);
        return select.getFirstSelectedOption().getText().trim();

    }

    public void openFirstTransaction() {
        transactionTableFirstTransaction.click();
    }
}
