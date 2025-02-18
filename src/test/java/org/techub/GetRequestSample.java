package org.techub;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.hasItem;
import org.testng.asserts.Assertion.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class GetRequestSample {

    String baseURI;
    String basePath;
    Response response;
    String url = "https://api.openbrewerydb.org/breweries";
    String gorestUrl;

    final static Logger logger = LoggerFactory.getLogger(GetRequestSample.class);

    @BeforeClass
    void setUp(){
        baseURI="https://api.openbrewerydb.org/breweries";
        basePath="?by_type=micro";
        gorestUrl = "https://gorest.co.in/public/v2/users";
    }

    @Test(enabled = true)
    void GetMethodTest(){

        given()
                .when()
                .get("https://api.openbrewerydb.org/breweries")
                .then()
                .statusCode(200).statusLine("HTTP/1.1 200 OK")
                .log().all()
                .assertThat()
                .body("name", hasItem("Bnaf, LLC")) .and().assertThat()
                .body("brewery_type", hasItem("planning"));
    }

    @Test(enabled = false)
    void GetMethodTestByMacro(){
        given().queryParam(basePath)
                .when()
                .get(baseURI)
                .then()
                .statusCode(200).statusLine("HTTP/1.1 200 OK")
                .log().all()
                .assertThat()
                .body("name", hasItem("(405) Brewing Co"));

    }

    @Test(testName = "exploratory Test", enabled = true )
    void ExploreRestAssured(){
        given()
                .when().
                 get(url)
                .then().statusCode(200).contentType("application/json; charset=utf-8")
                .log().all()
                .assertThat()
                .body("name", hasItem("(405) Brewing Co"));

    }










}
