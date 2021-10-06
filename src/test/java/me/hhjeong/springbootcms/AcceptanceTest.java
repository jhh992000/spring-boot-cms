package me.hhjeong.springbootcms;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import me.hhjeong.springbootcms.common.utils.DatabaseCleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {"spring.config.location=classpath:/application-test.properties"}
)
public abstract class AcceptanceTest {

    @LocalServerPort
    int port;

    @Autowired
    protected DatabaseCleanup databaseCleanup;

    public abstract void setUp();


    public static ExtractableResponse<Response> get(String uri) {
        return RestAssured
            .given().log().all()
            .when().get(uri)
            .then().log().all().extract();
    }

    public static ExtractableResponse<Response> get(String uri, String bearerToken) {
        return RestAssured
            .given().log().all()
            .headers("Authorization",
                "Bearer " + bearerToken,
                "Content-Type",
                MediaType.APPLICATION_JSON_VALUE,
                "Accept",
                MediaType.APPLICATION_JSON_VALUE)
            .when().get(uri)
            .then().log().all().extract();
    }

    public static ExtractableResponse<Response> post(Object params, String uri) {
        return RestAssured
            .given().log().all()
            .body(params)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().post(uri)
            .then().log().all().extract();
    }

    public static ExtractableResponse<Response> post(Object params, String uri, String bearerToken) {
        return RestAssured
            .given().log().all()
            .headers("Authorization",
                "Bearer " + bearerToken,
                "Content-Type",
                MediaType.APPLICATION_JSON_VALUE,
                "Accept",
                MediaType.APPLICATION_JSON_VALUE)
            .body(params)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().post(uri)
            .then().log().all().extract();
    }

    public static ExtractableResponse<Response> put(Object params, String uri) {
        return RestAssured
            .given().log().all()
            .body(params)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().put(uri)
            .then().log().all().extract();
    }

    public static ExtractableResponse<Response> put(Object params, String uri, String bearerToken) {
        return RestAssured
            .given().log().all()
            .headers("Authorization",
                "Bearer " + bearerToken,
                "Content-Type",
                MediaType.APPLICATION_JSON_VALUE,
                "Accept",
                MediaType.APPLICATION_JSON_VALUE)
            .body(params)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().put(uri)
            .then().log().all().extract();
    }

    public static ExtractableResponse<Response> delete(String uri) {
        return RestAssured.given().log().all()
            .when()
            .delete(uri)
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> delete(String uri, String bearerToken) {
        return RestAssured.given().log().all()
            .headers("Authorization",
                "Bearer " + bearerToken,
                "Content-Type",
                MediaType.APPLICATION_JSON_VALUE,
                "Accept",
                MediaType.APPLICATION_JSON_VALUE)
            .when().delete(uri)
            .then().log().all()
            .extract();
    }

    public static void assertResponseCode(ExtractableResponse<Response> response, HttpStatus httpStatus) {
        assertThat(response.statusCode()).isEqualTo(httpStatus.value());
    }
}