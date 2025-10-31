package ACA;


import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;


public class ApiTest {
    public static void main(String[] args) {

        Response response = RestAssured.get("https://httpbin.org/get");
        System.out.println(response.getBody().asString());
    }
}