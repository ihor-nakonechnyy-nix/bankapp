package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AccountsOverviewPage {
    private WebDriver driver;

    public AccountsOverviewPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//h1[contains(text(), 'Accounts Overview')]")
    WebElement accountsOverviewTitle;

    @FindBy(xpath = "//table[@id='accountTable']")
    WebElement accountsTable;

    @FindBy(xpath = "//table[@id='accountTable']//tr[1]/td[2]")
    WebElement balanceValue;

    @FindBy(xpath = "//table[@id='accountTable']//tr[2]/td[2]")
    WebElement totalValue;

    @FindBy(xpath = "//table[@id='accountTable']//tr[1]/td[3]")
    WebElement availableAmountValue;

    @FindBy(xpath = "//a[text()='13455']/../following-sibling::td[1]")
    WebElement accountFromBalance;

    @FindBy(xpath = "//a[text()='13566']/../following-sibling::td")
    WebElement accountToBalance;

    public double getInitialAccountAmountIndex(int index) {
        String initialAccountAmountValue = driver.findElement(By.xpath("//tr[@class='ng-scope'][" + index + "]/td[2]"))
                .getText()
                .replace("$", "")
                .replace(",", "")
                .trim();
        return Double.parseDouble(initialAccountAmountValue);

    }

    public double getNewAccountAmountIndex(int index) {
        String newAccountAmountValue = driver.findElement(By.xpath("//tr[@class='ng-scope'][" + index + "]/td[2]"))
                .getText()
                .replace("$", "")
                .replace(",", "")
                .trim();
        return Double.parseDouble(newAccountAmountValue);

    }

    public boolean isPageOpened() {
        return accountsOverviewTitle.isDisplayed();
    }

    public String getBalance() {
        return balanceValue.getText().replace("$", "").trim();
    }

    public String getAvailableAmount() {
        return availableAmountValue.getText().replace("$", "").trim();
    }

    public String getTotal() {
        return totalValue.getText().replace("$", "").trim();
    }

    public String getAccountFromBalance() {
        return accountFromBalance.getText().replace("$", "").trim();
    }

    public String getAccountToBalance() {
        return accountToBalance.getText().replace("$", "").trim();
    }
}
