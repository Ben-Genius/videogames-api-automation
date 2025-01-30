package com.videogames.controllers;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import com.videogames.config.ConfigurationReader;
import io.qameta.allure.Step;

public class AuthController {
    private final String authEndpoint;

    public AuthController() {
        this.authEndpoint = ConfigurationReader.getAuthEndpoint();
    }

    @Step("Authenticate user")
    public Response authenticate(String username, String password) {
        String credentials = String.format("{"
            + "\"username\": \"%s\","
            + "\"password\": \"%s\""
            + "}", username, password);

        return given()
            .contentType("application/json")
            .body(credentials)
            .when()
            .post(authEndpoint);
    }
}