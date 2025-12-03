package RGA;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class RGA_3 {
    private final int customerId = 12323;
    private final double balance = 2014.76;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/parabank/services/bank";
    }

    @Test
    public void userHasAnAccountAfterCreation() {
        Response getResponse = given()
                .accept(ContentType.XML)
                .when()
                .get("/customers/" + customerId + "/accounts")
                .then()
                .extract().response();

        // 2. Перевіряємо статус-код
        assertEquals(getResponse.statusCode(), 200, "Get request failed!");

        // 3. Парсимо XML
        XmlPath xml = new XmlPath(getResponse.asString());

        // 4. Отримуємо список ID рахунків
        int accountCount = xml.getList("accounts.account.id").size();
        assertEquals(accountCount, 1, "User should have exactly one account");

        // 5. Отримуємо баланс першого (і єдиного) рахунку
        double actualBalance = Double.parseDouble(xml.getString("accounts.account.balance[0]"));
        assertEquals(actualBalance, balance, "Account balance does not match expected default value");

        // 6. Друк для дебагу
        System.out.println("Account count: " + accountCount);
        System.out.println("Account balance: " + actualBalance);
    }
}
