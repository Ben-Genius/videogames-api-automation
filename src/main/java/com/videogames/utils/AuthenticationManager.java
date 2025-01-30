package com.videogames.utils;

import org.json.JSONObject;

import com.videogames.config.ConfigurationReader;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AuthenticationManager {
    private static String authToken = null;

    public static String getAuthToken() {
        if (authToken == null) {
            JSONObject authRequest = new JSONObject();
            authRequest.put("username", ConfigurationReader.getProperty("auth.username"));
            authRequest.put("password", ConfigurationReader.getProperty("auth.password"));

            Response response = RestAssured.given()
                .baseUri(ConfigurationReader.getBaseUrl())
                .header("Content-Type", "application/json")
                .body(authRequest.toString())
                .post(ConfigurationReader.getAuthEndpoint());

            if (response.getStatusCode() == 200) {
                authToken = response.jsonPath().getString("token");
            } else {
                throw new RuntimeException("Authentication failed: " + response.getStatusCode());
            }
        }
        return authToken;
    }

    public static void clearToken() {
        authToken = null;
    }
}
