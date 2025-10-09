package RGA;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class RGA_2 {

    private final int customerId = 12434;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/parabank/services/bank";
    }

    @Test
    public void testUpdateAndFetchCustomerData() {

        // 1. Надсилаємо запит ОНОВЛЕННЯ з параметрами (без JSON)
        Response updateResponse = given()
                .contentType(ContentType.JSON)
                .when()
                .post("/customers/update/" + customerId +
                        "?firstName=Ihor" +
                        "&lastName=Kovalenko" +
                        "&street=Kyivska" +
                        "&city=Kyiv" +
                        "&state=Kyivska" +
                        "&zipCode=12345" +
                        "&phoneNumber=380991112233" +
                        "&ssn=555-66-7777" +
                        "&username=ihor" +
                        "&password=demo")
                .then()
                .extract().response();

        System.out.println("Update response status: " + updateResponse.statusCode());
        System.out.println("Update response body: " + updateResponse.asString());

        assertEquals(updateResponse.statusCode(), 200, "❌ Update request failed!");

        // 🔹 2. Отримуємо користувача для перевірки
        Response getResponse = given()
                .accept(ContentType.XML)
                .when()
                .get("/customers/" + customerId)
                .then()
                .extract().response();

        System.out.println("Fetch response body: " + getResponse.asString());
        assertEquals(getResponse.statusCode(), 200, "❌ Failed to fetch user after update!");

        // 🔹 3. Перевіряємо отримані дані
        XmlPath xml = new XmlPath(getResponse.asString());
        String firstName = xml.getString("customer.firstName");
        String lastName = xml.getString("customer.lastName");
        String city = xml.getString("customer.address.city");

        assertEquals(firstName, "Ihor");
        assertEquals(lastName, "Kovalenko");
        assertEquals(city, "Kyiv");

        System.out.println("✅ User updated and verified successfully!");
    }
}