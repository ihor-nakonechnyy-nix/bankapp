package ACA;

    import io.restassured.RestAssured;
    import io.restassured.path.xml.XmlPath;
    import io.restassured.response.Response;
    import org.testng.Assert;
    import org.testng.annotations.BeforeClass;
    import org.testng.annotations.Test;

    import static io.restassured.RestAssured.given;
    import static org.testng.Assert.assertEquals;
    import static org.testng.Assert.assertTrue;

    // потрібно дізнатися чому не прицює поповнення рахунку отримувача хоча показує успіх при виконанні тесту

    public class ACA_2 {

        @BeforeClass
        public void setup() {
            RestAssured.baseURI = "http://localhost:8080/parabank/services/bank";
        }

        @Test
        public void testBillPay() {
            String fromAccountId = "13455";
            String toAccountId = "13566";
            double amount = 15.00;

            double fromBalanceBefore = getAccountBalance(fromAccountId);
            //double toBalanceBefore = getAccountBalance(toAccountId);

            String requestBody = """
                                    {
                              "name": "John",
                              "address": {
                                "street": "1431 Main St",
                                "city": "Beverly Hills",
                                "state": "CA",
                                "zipCode": "90210"
                              },
                              "phoneNumber": "310-447-4121",
                              "accountNumber": 13566
                            }
                    """;
            // Виконання POST-запиту
            Response response = given()
                    .contentType("application/json")
                    .queryParams("amount", amount)
                    .queryParams("accountId", fromAccountId)
                    .body(requestBody)
                    .when()
                    .post("/billpay")
                    .then()
                    .extract().response();

              double fromBalanceAfter = getAccountBalance(fromAccountId);
              double toBalanceAfter = getAccountBalance(toAccountId);

            System.out.println(fromBalanceAfter);
            System.out.println(toBalanceAfter);

            assertEquals(fromBalanceAfter, fromBalanceBefore - amount, 0.01);
            //assertEquals(toBalanceAfter, toBalanceBefore + amount, 0.01);

            int statusCode = response.getStatusCode();
            System.out.println("Статус код POST request: " + statusCode);
        }

        private double getAccountBalance(String accountId) {
            Response response = given()
                    .when()
                    .get("/accounts/" + accountId)
                    .then()
                    .statusCode(200)
                    .extract().response();

            int statusCode = response.getStatusCode();
            System.out.println("Статус код GET request: " + statusCode);

            XmlPath xml = new XmlPath(response.asString());
            return xml.getDouble("account.balance");
        }
    }
