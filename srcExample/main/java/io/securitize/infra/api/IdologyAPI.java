package io.securitize.infra.api;

import io.restassured.response.Response;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;


public class IdologyAPI extends AbstractAPI {
    public boolean isIdologyUp() {

        String idologyApiBaseUrl = MainConfig.getProperty(MainConfigProperty.idologyApiBaseUrl);

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("username", Users.getProperty(UsersProperty.idologyUsername));
        queryParams.put("password", Users.getProperty(UsersProperty.idologyPassword));

        // all these parameters are meaningless as we should always get 200 response with possible
        // error details in the body - as long as we have all these parameters in our request it should
        // work and provde idology is up
        queryParams.put("firstName", "John");
        queryParams.put("lastName", "Smith");
        queryParams.put("zip", "99999");
        queryParams.put("address", "test address");

        Response response = given()
                .port(80)
                .log().all()
                .queryParams(queryParams)
                .when()
                .post(idologyApiBaseUrl)
                .then()
                .log().all()
                .extract().response();

        return response.statusCode() == 200;
    }
}