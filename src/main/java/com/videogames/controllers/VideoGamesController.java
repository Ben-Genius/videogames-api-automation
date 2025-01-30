package com.videogames.controllers;

import com.videogames.config.ConfigurationReader;
import com.videogames.models.VideoGame;
import com.videogames.utils.AuthenticationManager;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class VideoGamesController {
    
    public VideoGamesController() {
        RestAssured.baseURI = ConfigurationReader.getBaseUrl();
    }

    @Step("Get all video games")
    public Response getAllVideoGames() {
        return RestAssured.given()
            .header("Accept", "application/json")
            .header("Authorization", "Bearer " + AuthenticationManager.getAuthToken())
            .get(ConfigurationReader.getVideoGamesEndpoint());
    }

    @Step("Get video game by ID: {id}")
    public Response getVideoGameById(Integer id) {
        return RestAssured.given()
            .header("Accept", "application/json")
            .header("Authorization", "Bearer " + AuthenticationManager.getAuthToken())
            .get(ConfigurationReader.getProperty("endpoint.videogame")
                .replace("{id}", id.toString()));
    }

    @Step("Create new video game")
    public Response createVideoGame(VideoGame videoGame) {
        return RestAssured.given()
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .header("Authorization", "Bearer " + AuthenticationManager.getAuthToken())
            .body(videoGame)
            .post(ConfigurationReader.getVideoGamesEndpoint());
    }

    @Step("Update video game with ID: {id}")
    public Response updateVideoGame(Integer id, VideoGame videoGame) {
    return RestAssured.given()
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .header("Authorization", "Bearer " + AuthenticationManager.getAuthToken())
        .body(videoGame)  // Send the JSON request body
        .put(ConfigurationReader.getProperty("endpoint.videogame")
                .replace("{id}", id.toString()));
}


    @Step("Delete video game with ID: {id}")
    public Response deleteVideoGame(Integer id) {
        return RestAssured.given()
            .header("Accept", "application/json")
            .header("Authorization", "Bearer " + AuthenticationManager.getAuthToken())
            .delete(ConfigurationReader.getProperty("endpoint.videogame")
                .replace("{id}", id.toString()));
    }
}
