package com.videogames.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.videogames.controllers.VideoGamesController;
import com.videogames.models.VideoGame;
import com.videogames.reporting.TestListener;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;

@Listeners(TestListener.class)
@Epic("Video Games API")
@Feature("Video Games CRUD Operations")
public class VideoGamesTests extends BaseTest {
    private VideoGamesController videoGamesController;
    private static final int EXISTING_GAME_ID = 1; // Use a known existing game ID

    @BeforeClass(alwaysRun = true)
    public void init() {
    videoGamesController = new VideoGamesController();
    }


@Test(groups = {"smoke", "positive"})
@Description("Verify complete video game lifecycle")
@Severity(SeverityLevel.CRITICAL)
public void testCompleteVideoGameLifecycle() {
    // Create
    VideoGame newGame = VideoGame.builder()
        .id(11)  // Explicitly set an ID
        .name("Super Mario Galaxy")
        .releaseDate("2024-01-30")
        .reviewScore(95)
        .category("Platform")
        .rating("E")
        .build();

    Response createResponse = videoGamesController.createVideoGame(newGame);
    assertEquals(createResponse.getStatusCode(), 200);
    
    // Read
    Response getResponse = videoGamesController.getVideoGameById(1); // Use existing game ID
    assertEquals(getResponse.getStatusCode(), 200);
    
    // Update
    VideoGame updatedGame = VideoGame.builder()
        .id(1)  // Include ID for update
        .name("Super Mario Galaxy 2")
        .releaseDate("2024-01-30")
        .reviewScore(98)
        .category("Platform")
        .rating("E")
        .build();
        
    Response updateResponse = videoGamesController.updateVideoGame(1, updatedGame);
    assertEquals(updateResponse.getStatusCode(), 200);

    // Delete
    Response deleteResponse = videoGamesController.deleteVideoGame(1);
    assertEquals(deleteResponse.getStatusCode(), 200);
}


@Test(groups = {"smoke", "positive"})
@Description("Verify video game API responses in read-only mode")
@Severity(SeverityLevel.CRITICAL)
public void testVideoGameApiResponses() {
    // Create attempt (should return 200 even in read-only mode)
    VideoGame newGame = VideoGame.builder()
            .name("Super Mario Galaxy")
            .releaseDate("2024-01-30")
            .reviewScore(95)
            .category("Platform")
            .rating("E")
            .build();

    Response createResponse = videoGamesController.createVideoGame(newGame);
    assertEquals(createResponse.getStatusCode(), 200);

    // Read existing game (using known ID from static data)
    Response getResponse = videoGamesController.getVideoGameById(1);
    assertEquals(getResponse.getStatusCode(), 200);
    assertNotNull(getResponse.jsonPath().getString("name"));
}


@Test(groups = {"regression"})
@Description("Get all video games")
public void testGetAllVideoGames() {
    Response response = videoGamesController.getAllVideoGames();
    assertEquals(response.getStatusCode(), 200);
    assertNotNull(response.jsonPath().getList(""));
}

@Test(groups = {"regression"})
@Description("Get video game by ID")
public void testGetVideoGameById() {
    Response response = videoGamesController.getVideoGameById(1);
    assertEquals(response.getStatusCode(), 200);
    assertNotNull(response.jsonPath().getString("name"));
}

@Test(groups = {"regression"})
@Description("Create new video game")
public void testCreateVideoGame() {
    VideoGame game = VideoGame.builder()
        .name("New Test Game")
        .releaseDate("2024-01-30")
        .reviewScore(85)
        .category("Action")
        .rating("T")
        .build();
    
    Response response = videoGamesController.createVideoGame(game);
    assertEquals(response.getStatusCode(), 200);
}


    @Test(groups = {"smoke", "positive"})
    @Description("Verify video game read operations")
    @Severity(SeverityLevel.CRITICAL)
  public void testVideoGameReadOperations() {
      // Get all games
      Response getAllResponse = videoGamesController.getAllVideoGames();
      assertEquals(getAllResponse.getStatusCode(), 200);
      assertNotNull(getAllResponse.jsonPath().getList(""));

      // Get specific game
      Response getOneResponse = videoGamesController.getVideoGameById(EXISTING_GAME_ID);
      assertEquals(getOneResponse.getStatusCode(), 200);
      assertNotNull(getOneResponse.jsonPath().getString("name"));
  }
    @Test(groups = {"smoke", "positive"})
    @Description("Verify create video game request structure")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateVideoGameRequest() {
        VideoGame newGame = VideoGame.builder()
            .name("Super Mario Galaxy")
            .releaseDate("2024-01-30")
            .reviewScore(95)
            .category("Platform")
            .rating("E")
            .build();

        Response createResponse = videoGamesController.createVideoGame(newGame);
        // In read-only mode, we expect a 200 response even though data won't persist
        assertEquals(createResponse.getStatusCode(), 200);
    }
    @Test(groups = {"smoke", "positive"})
    @Description("Verify update video game request structure")
    @Severity(SeverityLevel.CRITICAL)
    public void testUpdateVideoGameRequest() {
        VideoGame updatedGame = VideoGame.builder()
                .id(EXISTING_GAME_ID)
                .name("Updated Game")
                .releaseDate("2024-01-30")
                .reviewScore(98)
                .category("Platform")
                .rating("E")
                .build();

        Response updateResponse = videoGamesController.updateVideoGame(EXISTING_GAME_ID, updatedGame);
        // In read-only mode, we expect a 200 response even though data won't persist
        assertEquals(updateResponse.getStatusCode(), 200);
    }

    @Test(groups = {"smoke", "positive"})
    @Description("Verify delete video game request structure")
    @Severity(SeverityLevel.CRITICAL)
    public void testDeleteVideoGameRequest() {
        Response deleteResponse = videoGamesController.deleteVideoGame(EXISTING_GAME_ID);
        // In read-only mode, we expect a 200 response even though data won't persist
        assertEquals(deleteResponse.getStatusCode(), 200);
    }
     @Test(groups = {"regression", "negative"})
    @Description("Verify handling of invalid video game data")
    @Severity(SeverityLevel.NORMAL)
    public void testInvalidVideoGameData() {
        VideoGame invalidGame = VideoGame.builder()
            .name("")
            .releaseDate("invalid-date")
            .reviewScore(-1)
            .category("")
            .rating("")
            .build();

        Response response = videoGamesController.createVideoGame(invalidGame);
        assertEquals(response.getStatusCode(), 400);
    }
    @Test(groups = {"smoke", "positive"})
    @Description("Verify updating a video game")
    @Severity(SeverityLevel.CRITICAL)
    public void testUpdateVideoGame() {
    // Use a known existing ID (like 1) instead of trying to create and update
    VideoGame updatedGame = VideoGame.builder()
            .id(1)  // Use existing ID
            .name("Updated Game Name")
            .releaseDate("2024-02-02")
            .reviewScore(90)
            .category("Action")
            .rating("Mature")
            .build();
            
    Response updateResponse = videoGamesController.updateVideoGame(1, updatedGame);
    assertEquals(updateResponse.getStatusCode(), 200);
}
    @Test(groups = {"smoke", "positive"})
    @Description("Verify delete video game request structure")
    @Severity(SeverityLevel.CRITICAL)
    public void testDeleteVideoGame() {
        // Use a known existing ID instead of trying to create and delete
        Response deleteResponse = videoGamesController.deleteVideoGame(1); // Using ID 1 which should exist
        assertEquals(deleteResponse.getStatusCode(), 200);
    }

    @Test(groups = { "regression", "negative" })

    @Description("Verify delete operation with invalid game ID")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteInvalidGameId() {
        Response deleteResponse = videoGamesController.deleteVideoGame(0);  // ID 0 should be invalid
        assertEquals(deleteResponse.getStatusCode(), 404);
    }

      @Test(groups = {"regression", "negative"})
    @Description("Verify handling of non-existent video game ID")
    @Severity(SeverityLevel.NORMAL)
    public void testNonExistentGameId() {
        Response response = videoGamesController.getVideoGameById(999999);
        assertEquals(response.getStatusCode(), 404);
    }

}