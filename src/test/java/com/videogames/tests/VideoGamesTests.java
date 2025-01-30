package com.videogames.tests;

import com.videogames.controllers.VideoGamesController;
import com.videogames.models.VideoGame;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

@Feature("Video Games")
public class VideoGamesTests extends BaseTest {
    private VideoGamesController videoGamesController;

    @BeforeClass
    public void init() {
        videoGamesController = new VideoGamesController();
    }

    @Test
    @Description("Verify getting all video games")
    public void testGetAllVideoGames() {
        Response response = videoGamesController.getAllVideoGames();
        assertEquals(response.getStatusCode(), 200);
    }

    @Test
    @Description("Verify getting video game by ID")
    public void testGetVideoGameById() {
            Integer validGameId = 1; // Ensure this ID exists in the GET response

            Response response = videoGamesController.getVideoGameById(validGameId);
    System.out.println("GET /videogame/" + validGameId + " - Response Code: " + response.getStatusCode());
    System.out.println("GET /videogame/" + validGameId + " - Response Body: " + response.getBody().asString());
        assertEquals(response.getStatusCode(), 200);
    }

    @Test
    @Description("Verify creating new video game")
    public void testCreateVideoGame() {
        VideoGame newGame = VideoGame.builder()
           .name("Super Mario")
        .releaseDate("2023-01-01 23:59:59") // Adjusted format
        .reviewScore(95)
        .category("Platform")
        .rating("Universal") // Changed from "E" to match API format
            .build();

        Response response = videoGamesController.createVideoGame(newGame);
            System.out.println("POST /videogames - Response Code: " + response.getStatusCode());
    System.out.println("POST /videogames - Response Body: " + response.getBody().asString());

        assertEquals(response.getStatusCode(), 200);
    }
}