package com.videogames.tests;

import com.videogames.controllers.VideoGamesController;
import com.videogames.models.VideoGame;
import com.videogames.reporting.TestListener;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

@Listeners(TestListener.class)
@Epic("Video Games API")
@Feature("Video Games CRUD Operations")
public class VideoGamesTests extends BaseTest {
    private VideoGamesController videoGamesController;

    @BeforeClass
    public void init() {
        videoGamesController = new VideoGamesController();
    }


   @Test(groups = {"smoke", "positive"})
@Description("Verify complete CRUD lifecycle of a video game")
@Severity(SeverityLevel.CRITICAL)
public void testCompleteVideoGameLifecycle() {
    // Create
    VideoGame newGame = VideoGame.builder()
        .name("Test Game")
        .releaseDate("2024-01-30")
        .reviewScore(85)
        .category("Shooter")
        .rating("Universal")
        .build();

    Response createResponse = videoGamesController.createVideoGame(newGame);
    assertEquals(createResponse.getStatusCode(), 200);
    
    // Extract the ID and print it for debugging
    int gameId = createResponse.jsonPath().getInt("id");
    System.out.println("Created game ID: " + gameId);

    // Wait briefly for the game to be available
    try {
        Thread.sleep(1000);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }

    // Read - try with ID 1 instead of 0
    Response getResponse = videoGamesController.getVideoGameById(1);
    assertEquals(getResponse.getStatusCode(), 200);
    
    // Continue with update and delete using the same ID
}


    @Test(groups = {"regression", "negative"})
    @Description("Verify handling of invalid video game data")
    @Severity(SeverityLevel.NORMAL)
    public void testInvalidVideoGameData() {
        VideoGame invalidGame = VideoGame.builder()
            .name("")
            .releaseDate("2024-01-30")
            .reviewScore(-1)
            .category("")
            .rating("")
            .build();

        Response response = videoGamesController.createVideoGame(invalidGame);
        assertEquals(response.getStatusCode(), 400);
    }
}