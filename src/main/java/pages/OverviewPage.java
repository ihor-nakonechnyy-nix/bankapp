package pages;

import org.openqa.selenium.WebDriver; // основний інтерфейс Selenium для керування браузером.
import org.openqa.selenium.By; // фабрика локаторів (By.id, By.xpath, By.cssSelector тощо).
import org.openqa.selenium.WebElement; // представляє HTML-елемент (input, tr, td, a).

import java.util.List; // стандартний Java List.

public class OverviewPage {
    private WebDriver driver;

    // Конструктор для OverviewPage Page
    public OverviewPage(WebDriver driver) {
        this.driver = driver;
    }

    // Локатори
    List<WebElement> accounts = driver.findElements(By.xpath("//table[@id='accountTable']/tbody/tr"));

    public List<WebElement> getAccounts() {
        return driver.findElements(By.xpath("//table[@id='accountTable']/tbody/tr"));
    }
}
