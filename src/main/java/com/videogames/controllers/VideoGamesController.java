// VideoGamesController.java
package com.videogames.controllers;

import com.videogames.config.ConfigurationReader;
import com.videogames.models.VideoGame;
import com.videogames.utils.AuthenticationManager;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class VideoGamesController {
    
    public VideoGamesController() {
        RestAssured.baseURI = ConfigurationReader.getBaseUrl();
    }

    @Step("Get all video games")
    public Response getAllVideoGames() {
        RequestSpecification request = RestAssured.given()
            .header("Accept", "application/json")
            .header("Authorization", "Bearer " + AuthenticationManager.getAuthToken());
        
        return request.get(ConfigurationReader.getVideoGamesEndpoint());
    }

    @Step("Get video game by ID")
    public Response getVideoGameById(Integer id) {
        RequestSpecification request = RestAssured.given()
            .header("Accept", "application/json")
            .header("Authorization", "Bearer " + AuthenticationManager.getAuthToken());
        
        String endpoint = ConfigurationReader.getProperty("endpoint.videogame")
            .replace("{id}", id.toString());
        
        return request.get(endpoint);
    }

    @Step("Create new video game")
    public Response createVideoGame(VideoGame videoGame) {
        RequestSpecification request = RestAssured.given()
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .header("Authorization", "Bearer " + AuthenticationManager.getAuthToken())
            .body(videoGame);
        
        return request.post(ConfigurationReader.getVideoGamesEndpoint());
    }
}