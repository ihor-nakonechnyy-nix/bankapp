package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class ActivityPage {
    private WebDriver driver;

    public ActivityPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @FindBy(xpath = "//table[@id='transactionTable']//tr[1]/td[2]")
    WebElement transactionTableFirstTransaction;



    public void openFirstTransaction() {
        transactionTableFirstTransaction.click();
    }


}
