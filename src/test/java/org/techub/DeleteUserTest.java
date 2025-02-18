package org.techub;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DeleteUserTest {

    private static final String BASE_URL = "https://gorest.co.in/public/v2/users";
    private static final String TOKEN = "e15f685c8c4ae7f9abd64712552318ca871a98667a7d2de65ba434c779c6d0af"; // Use your token

    @DataProvider(name = "deleteUserData")
    public Object[][] provideDeleteUserData() {
        return new Object[][]{
                {7706032}// Delete user ID 67890
        };
    }

    @Test(dataProvider = "deleteUserData")
    public void deleteUser(int userId) {
        RestAssured.baseURI = BASE_URL;

        // Send DELETE request
        Response response = given()
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + TOKEN)
                .when()
                .delete("/" + userId)
                .then()
                .log().all()
                .extract().response();

        // Validate response
        Assert.assertEquals(response.getStatusCode(), 204, "Expected 204 No Content status code");

        // Verify user is deleted by trying to GET the deleted user
        Response getResponse = given()
                .header("Authorization", "Bearer " + TOKEN)
                .when()
                .get("/" + userId)
                .then()
                .log().all()
                .extract().response();

        Assert.assertEquals(getResponse.getStatusCode(), 404, "Expected 404 Not Found after deletion");
    }
}
