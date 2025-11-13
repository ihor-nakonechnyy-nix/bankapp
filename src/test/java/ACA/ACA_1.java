package ACA;

import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ACA_1 {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/parabank/services/bank";
    }

    @Test
    public void testTransferAndValidateBalances() {
        final int customerId = 12434;

        String fromAccountId = "13455"; // parasoft user
        String toAccountId = "12456"; //
        double transferAmount = 50.0;

        double fromBalanceBefore = getAccountBalance(fromAccountId);
        double toBalanceBefore = getAccountBalance(toAccountId);

        given()
                .queryParam("fromAccountId", fromAccountId)
                .queryParam("toAccountId", toAccountId)
                .queryParam("amount", transferAmount)
                .when()
                .post("/transfer")
                .then()
                .statusCode(200);

        double fromBalanceAfter = getAccountBalance(fromAccountId);
        double toBalanceAfter = getAccountBalance(toAccountId);

        System.out.println(fromBalanceAfter);
        System.out.println(toBalanceAfter);

        assertEquals(fromBalanceAfter, fromBalanceBefore - transferAmount,
                "Баланс вихідного рахунку не зменшився на потрібну суму");

        assertEquals(toBalanceAfter, toBalanceBefore + transferAmount,
                "Баланс цільового рахунку не збільшився на потрібну суму");

        String userData = given()
                .when()
                .get("/customers/12323")
                .then()
                .statusCode(200)
                .extract().asString();

    }

    private double getAccountBalance(String accountId) {
        Response response = given()
                .when()
                .get("/accounts/" + accountId)
                .then()
                .statusCode(200)
                .extract().response();

        XmlPath xml = new XmlPath(response.asString());
        return xml.getDouble("account.balance");

    }
}
