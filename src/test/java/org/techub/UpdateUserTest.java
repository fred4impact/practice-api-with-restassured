package org.techub;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class UpdateUserTest {

    private static final String BASE_URL = "https://gorest.co.in/public/v2/users";
    private static final String TOKEN = "e15f685c8c4ae7f9abd64712552318ca871a98667a7d2de65ba434c779c6d0af"; // Use your token

    @DataProvider(name = "updateUserData")
    public Object[][] provideUpdateUserData() {
        return new Object[][]{
                {7706032, "Matty Joe", "inactive"}
                // Update user ID 67890
        };
    }

    @Test(dataProvider = "updateUserData")
    public void updateUser(int userId, String newName, String newStatus) {
        RestAssured.baseURI = BASE_URL;

        // Create JSON request body
        String requestBody = String.format(
                "{\"name\":\"%s\", \"status\":\"%s\"}",
                newName, newStatus
        );

        // Send PUT request
        Response response = given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + TOKEN)
                .body(requestBody)
                .when()
                .put("/" + userId)
                .then()
                .log().all()
                .extract().response();

        // Validate response
        Assert.assertEquals(response.getStatusCode(), 200, "Expected 200 OK status code");
        Assert.assertEquals(response.jsonPath().getString("name"), newName);
        Assert.assertEquals(response.jsonPath().getString("status"), newStatus);
    }
}
