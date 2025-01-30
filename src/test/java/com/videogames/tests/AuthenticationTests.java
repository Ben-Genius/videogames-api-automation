package com.videogames.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.videogames.config.ConfigurationReader;
import com.videogames.controllers.AuthController;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;

@Feature("Authentication")
public class AuthenticationTests extends BaseTest {
    private AuthController authController;

    @BeforeClass
    public void init() {
        authController = new AuthController();
    }

    @Test
    @Description("Verify successful authentication with valid credentials")
    public void testSuccessfulAuthentication() {
        Response response = authController.authenticate(
            ConfigurationReader.getProperty("auth.username"),
            ConfigurationReader.getProperty("auth.password")
        );
        
        assertEquals(response.getStatusCode(), 200);
        assertNotNull(response.getBody().asString());
    }

   @Test
    @Description("Verify authentication fails with invalid credentials")
    public void testFailedAuthentication() {
    Response response = authController.authenticate("invalid", "invalid");
    
    System.out.println("Auth Failure Response: " + response.getStatusCode());
    System.out.println("Body: " + response.getBody().asString());

    assertEquals(response.getStatusCode(), 403); // Update if API returns 403
}

}