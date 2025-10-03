package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class OverViewPage {
    private WebDriver driver;

    // Конструктор для RequestLoan Page
    public OverViewPage(WebDriver driver) {
        this.driver = driver;
    }

    // Локатори
    List<WebElement> accounts = driver.findElements(By.xpath("//table[@id='accountTable']/tbody/tr"));

    public List<WebElement> getAccounts() {
        return driver.findElements(By.xpath("//table[@id='accountTable']/tbody/tr"));
    }






}
