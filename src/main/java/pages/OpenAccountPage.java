package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OpenAccountPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public OpenAccountPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    private By dropdownAccountType = By.id("type");
    private By openNewAccountBtn = By.cssSelector("input[value='Open New Account']");
    private By accountOpenedMessage = By.xpath("//div[@class='ng-scope']//h1[@class='title']");
    private By accountsOverviewBtn = By.xpath("//div[@id='leftPanel']//ul[1]/li[2]");

    public void selectAccountType(String visibleText) {
        Select select = new Select(driver.findElement(dropdownAccountType));
        select.selectByVisibleText(visibleText);
    }

    public void clickNewAccountButton() {
        driver.findElement(openNewAccountBtn).click();
    }

    public String getAccountOpenedMessageText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(accountOpenedMessage))
                .getText()
                .trim();
    }

    public void goToAccountsOverview() {
        driver.findElement(accountsOverviewBtn).click();
    }
}
