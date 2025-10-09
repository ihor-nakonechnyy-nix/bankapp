package RGA;
// Це папка (package), де лежить наш клас.
// Декларуємо, що клас належить до групи RGA.

import io.restassured.RestAssured;
// RestAssured – бібліотека, яка допомагає легко відправляти HTTP-запити (GET, POST і т.д.) та перевіряти відповіді.

import io.restassured.http.ContentType;
// Тут ми беремо готові типи даних (JSON, XML), щоб сказати серверу, що ми відправляємо чи хочемо отримати.

import io.restassured.path.xml.XmlPath;
// XmlPath дозволяє "розбирати" XML відповіді та діставати потрібні значення з нього.

import io.restassured.response.Response;
// Response – це клас, який зберігає все, що нам повертає сервер (статус, тіло відповіді).

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
// Анотації TestNG:
// @BeforeClass – метод виконується один раз перед усіма тестами в класі
// @Test – сам тестовий метод, який перевіряє поведінку програми.

import static io.restassured.RestAssured.given;
// "given" дозволяє будувати запит: що відправляємо, з якими параметрами.

import static org.testng.Assert.assertEquals;
// assertEquals перевіряє, чи два значення рівні. Якщо ні – тест падає.

public class RGA_2 {
// Це оголошення нашого тестового класу. Всі тести ми кладемо сюди.

    private final int customerId = 12434;
    // Це ID користувача, якого ми будемо оновлювати.
    // "final" означає, що його не можна змінити в коді.

    @BeforeClass
    public void setup() {
        // Цей метод виконується один раз перед усіма тестами цього класу.
        RestAssured.baseURI = "http://localhost:8080/parabank/services/bank";
        // Ми кажемо: "Базова адреса нашого сервера така".
        // Тепер у запитах не треба писати повний URL, тільки endpoint.
    }

    @Test
    public void testUpdateAndFetchCustomerData() {
        // Це сам тест. Тут ми оновлюємо користувача та перевіряємо, що зміни застосовані.

        // -----------------------------
        // 1️⃣ Відправляємо POST запит для оновлення даних користувача
        // -----------------------------
        Response updateResponse = given() // Починаємо будувати запит
                .contentType(ContentType.JSON)
                // Кажемо серверу: "Я відправляю JSON" (хоч у нашому випадку дані в URL)
                .when()
                // Коли ми готові відправити запит...
                .post("/customers/update/" + customerId + // ...POST на endpoint /customers/update/{id}
                        "?firstName=Ihor" +               // query-параметри – нові дані користувача
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
        // "extract().response()" забирає все, що нам повернув сервер: код відповіді, тіло, заголовки.

        // -----------------------------
        // 2️⃣ Друкуємо відповідь для перевірки
        // -----------------------------
        System.out.println("Update response status: " + updateResponse.statusCode());
        // Показуємо код відповіді (наприклад, 200 – успіх, 500 – помилка на сервері)
        System.out.println("Update response body: " + updateResponse.asString());
        // Показуємо тіло відповіді (може містити текст або XML/JSON)

        // -----------------------------
        // 3️⃣ Перевіряємо, що запит пройшов успішно
        // -----------------------------
        assertEquals(updateResponse.statusCode(), 200, "❌ Update request failed!");
        // Якщо сервер не повернув 200, тест впаде і виведе повідомлення

        // -----------------------------
        // 4️⃣ GET запит для підтвердження, що дані оновились
        // -----------------------------
        Response getResponse = given()
                .accept(ContentType.XML)
                // Ми кажемо серверу: "Я хочу отримати XML"
                .when()
                .get("/customers/" + customerId)
                // GET запит на endpoint /customers/{id} для отримання даних
                .then()
                .extract().response();

        System.out.println("Fetch response body: " + getResponse.asString());
        // Друкуємо тіло відповіді, щоб бачити, що сервер повернув

        assertEquals(getResponse.statusCode(), 200, "❌ Failed to fetch user after update!");
        // Перевіряємо, що GET теж пройшов успішно

        // -----------------------------
        // 5️⃣ Парсимо XML та перевіряємо конкретні значення
        // -----------------------------
        XmlPath xml = new XmlPath(getResponse.asString());
        // XmlPath допомагає діставати конкретні поля з XML

        String firstName = xml.getString("customer.firstName");
        String lastName = xml.getString("customer.lastName");
        String city = xml.getString("customer.address.city");
        // Тут ми дістаємо значення конкретних полів із відповіді

        assertEquals(firstName, "Ihor");
        assertEquals(lastName, "Kovalenko");
        assertEquals(city, "Kyiv");
        // Перевіряємо, що сервер оновив саме ті дані, які ми хотіли

        System.out.println("✅ User updated and verified successfully!");
        // Якщо всі перевірки пройшли, друкуємо повідомлення про успіх
    }
}
