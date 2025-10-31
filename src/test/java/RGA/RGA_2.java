package RGA; // Пакет (package), у якому знаходиться наш клас.

import io.restassured.RestAssured; // RestAssured — бібліотека для створення та відправлення HTTP-запитів.
import io.restassured.http.ContentType; // Містить перелік стандартних типів контенту (JSON, XML тощо).
import io.restassured.path.xml.XmlPath; // Дозволяє парсити XML-відповіді та діставати з них значення.
import io.restassured.response.Response; // Клас для збереження відповіді сервера (статус, тіло, заголовки).

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/*_______________________Requirements___________________________________
1. Change User's data upon API
2. Fetch user's data via API call
NOTE: After DB initializing there are always two users in the system.
Play with userId: 12323
________________________________________________________________________*/

// Анотації TestNG:
// @BeforeClass — метод виконується один раз перед усіма тестами цього класу.
// @Test — позначає тестовий метод.

import java.util.Map;

import static io.restassured.RestAssured.given; // "given" — початок побудови запиту (тіло, параметри, заголовки тощо).
import static org.testng.Assert.assertEquals; // assertEquals перевіряє, чи два значення однакові; інакше тест не проходить.

public class RGA_2 { // Оголошення тестового класу. Тут зберігаються всі тести.

    private final int customerId = 12434;
    // ID користувача, якого ми будемо оновлювати.
    // "final" означає, що значення не можна змінювати після ініціалізації.

    @BeforeClass
    public void setup() { // Виконується один раз перед усіма тестами цього класу.
        RestAssured.baseURI = "http://localhost:8080/parabank/services/bank";
        // Встановлюємо базову адресу сервера.
        // Тепер у запитах достатньо вказувати лише endpoint.
    }

    @Test
    public void testUpdateAndFetchCustomerData() { // Основний тест: оновлюємо дані користувача та перевіряємо, що зміни збережено.

        // це інтерфейс у Java, який представляє словник (асоціативний масив) або пари "ключ → значення".
        // У нашому випадку і ключ, і значення — це рядки (String).
        Map<String, String> postBody = Map.of(
                "firstName","Ihor",
                "lastName","Kovalenko",
                "street","Kyivska",
                "city","Kyiv",
                "state","Kyivska",
                "zipCode","12345",
                "phoneNumber","380991112233",
                "ssn","555-66-7777",
                "username","ihor",
                "password","demo"
        );

        // -----------------------------
        // 1️⃣ Відправляємо POST-запит для оновлення даних користувача
        // -----------------------------
        Response updateResponse = given() // Початок побудови запиту
                .contentType(ContentType.JSON) // Вказуємо, що передаємо дані у форматі JSON
                .queryParams(postBody) // Додаємо параметри запиту у вигляді ключ-значення
                .when() // Готові відправити запит
                .post("/customers/update/" + customerId) // Виконуємо POST на endpoint /customers/update/{id}
                .then()
                .extract().response(); // Отримуємо повну відповідь від сервера

        // -----------------------------
        // 2️⃣ Друкуємо відповідь сервера
        // -----------------------------
        System.out.println("Update response status: " + updateResponse.statusCode());
        // Код відповіді (200 – успішно, 400/500 – помилка)
        System.out.println("Update response body: " + updateResponse.asString());
        // Тіло відповіді (текст, XML або JSON)

        // -----------------------------
        // 3️⃣ Перевіряємо, що оновлення пройшло успішно
        // -----------------------------
        assertEquals(updateResponse.statusCode(), 200, "❌ Update request failed!");
        // Якщо код відповіді не 200, тест вважається проваленим

        // -----------------------------
        // 4️⃣ Виконуємо GET-запит, щоб перевірити оновлені дані
        // -----------------------------
        Response getResponse = given()
                .accept(ContentType.XML) // Вказуємо, що очікуємо відповідь у форматі XML
                .when()
                .get("/customers/" + customerId) // Отримуємо дані користувача за ID
                .then()
                .extract().response();

        System.out.println("Fetch response body: " + getResponse.asString());
        // Виводимо тіло відповіді для візуальної перевірки

        assertEquals(getResponse.statusCode(), 200, "❌ Failed to fetch user after update!");
        // Перевіряємо, що запит на отримання даних теж успішний

        // -----------------------------
        // 5️⃣ Парсимо XML-відповідь і перевіряємо конкретні поля
        // -----------------------------
        XmlPath xml = new XmlPath(getResponse.asString());
        // XmlPath дозволяє зручно діставати значення елементів XML за шляхом

        String firstName = xml.getString("customer.firstName");
        String lastName = xml.getString("customer.lastName");
        String city = xml.getString("customer.address.city");
        // Витягуємо потрібні дані з XML-відповіді

        assertEquals(firstName, "Ihor");
        assertEquals(lastName, "Kovalenko");
        assertEquals(city, "Kyiv");
        // Перевіряємо, що дані оновилися правильно

        System.out.println("✅ User updated and verified successfully!");
        // Якщо всі перевірки успішні, виводимо повідомлення про успіх
    }
}
