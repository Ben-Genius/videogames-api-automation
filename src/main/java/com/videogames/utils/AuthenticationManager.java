// AuthenticationManager.java
package com.videogames.utils;

import com.videogames.config.ConfigurationReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AuthenticationManager {
    private static String authToken = null;

    public static String getAuthToken() {
        if (authToken == null) {
            // Create authentication request body
            String authBody = "{\"username\": \"" + ConfigurationReader.getProperty("auth.username") + 
                            "\", \"password\": \"" + ConfigurationReader.getProperty("auth.password") + "\"}";

            // Set base URI for RestAssured
            RestAssured.baseURI = ConfigurationReader.getBaseUrl();

            // Make authentication request
            RequestSpecification request = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(authBody);

            Response response = request.post(ConfigurationReader.getAuthEndpoint());

            if (response.getStatusCode() == 200) {
                authToken = response.jsonPath().getString("token");
            } else {
                throw new RuntimeException("Authentication failed: " + response.getStatusCode() + " - " + response.getBody().asString());
            }
        }
        return authToken;
    }
}