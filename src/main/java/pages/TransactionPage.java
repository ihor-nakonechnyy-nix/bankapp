package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TransactionPage {
    private WebDriver driver;

    // Конструктор для ActivityPage Page
    public TransactionPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getTransactionID() {
        return driver.findElement(By.xpath("//td[normalize-space()='Transaction ID:']/following-sibling::td")).getText();
    }

    public String getDate() {
        return driver.findElement(By.xpath("//td[normalize-space()='Date:']/following-sibling::td")).getText();
    }

    public String getDescription() {
        return driver.findElement(By.xpath("//td[normalize-space()='Description:']/following-sibling::td")).getText();
    }

    public String getType() {
        return driver.findElement(By.xpath("//td[normalize-space()='Type:']/following-sibling::td")).getText();
    }

    public String getAmount() {
        return driver.findElement(By.xpath("//td[normalize-space()='Amount:']/following-sibling::td")).getText();
    }
}
