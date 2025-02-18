package org.techub;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class CreateUserTest {

    private static final String BASE_URL = "https://gorest.co.in/public/v2/users";
    private static final String TOKEN = "e15f685c8c4ae7f9abd64712552318ca871a98667a7d2de65ba434c779c6d0af"; // Use your token

    @DataProvider(name = "userData")
    public Object[][] provideUserData() {
        return new Object[][]{
                {"Joe Thepaley", "male", "joe.thepaley@rest.com", "active"}
        };
    }

    @Test(dataProvider = "userData")
    public void createUser(String name, String gender, String email, String status) {
        RestAssured.baseURI = BASE_URL;

        // Create JSON request body
        String requestBody = String.format(
                "{\"name\":\"%s\", \"gender\":\"%s\", \"email\":\"%s\", \"status\":\"%s\"}",
                name, gender, email, status
        );

        // Send POST request
        Response response = given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + TOKEN)
                .body(requestBody)
                .when()
                .post()
                .then()
                .log().all()
                .extract().response();

        // Validate response
        Assert.assertEquals(response.getStatusCode(), 201, "Expected 201 Created status code");
        Assert.assertEquals(response.jsonPath().getString("name"), name);
        Assert.assertEquals(response.jsonPath().getString("gender"), gender);
        Assert.assertEquals(response.jsonPath().getString("email"), email);
        Assert.assertEquals(response.jsonPath().getString("status"), status);
    }
}
