package com.videogames.utils;

import com.videogames.config.ConfigurationReader;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

@Slf4j
public class AuthenticationManager {
    private static String authToken = null;

    @Step("Authenticate with the API")
    public static String getAuthToken() {
        if (authToken == null) {
            JSONObject authBody = new JSONObject()
                .put("username", ConfigurationReader.getProperty("auth.username"))
                .put("password", ConfigurationReader.getProperty("auth.password"));

            log.info("Attempting to authenticate with API");
            Response response = RestAssured.given()
                .baseUri(ConfigurationReader.getBaseUrl())
                .header("Content-Type", "application/json")
                .body(authBody.toString())
                .post(ConfigurationReader.getAuthEndpoint());

            if (response.getStatusCode() == 200) {
                authToken = response.jsonPath().getString("token");
                log.info("Successfully authenticated with API");
            } else {
                log.error("Authentication failed with status code: {}", response.getStatusCode());
                throw new RuntimeException("Authentication failed: " + response.getStatusCode());
            }
        }
        return authToken;
    }
}