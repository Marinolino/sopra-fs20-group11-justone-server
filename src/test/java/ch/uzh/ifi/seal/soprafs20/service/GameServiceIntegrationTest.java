package ch.uzh.ifi.seal.soprafs20.service;

import ch.uzh.ifi.seal.soprafs20.constant.ClueStatus;
import ch.uzh.ifi.seal.soprafs20.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs20.entity.Game.Clue;
import ch.uzh.ifi.seal.soprafs20.entity.Game.Game;
import ch.uzh.ifi.seal.soprafs20.exceptions.API.POST.PostRequestException409;
import ch.uzh.ifi.seal.soprafs20.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebAppConfiguration
@SpringBootTest
class GameServiceIntegrationTest {

    Game testGame;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameService gameService;


    @BeforeEach
    public void setup() {
        testGame = new Game();
        gameRepository.deleteAll();
    }

    @Test
    public void getAllGames_noGamesInRepo(){
        List<Game> games = gameService.getGames();

        assertEquals(games.size(), 0);
    }

    @Test
    public void createGame_success() throws Exception {
        testGame.setCurrentUserId((long)1);

        Game createdGame = gameService.createGame(testGame);

        assertEquals(createdGame.getScore(), 0);
        assertEquals(createdGame.getRound(), 0);
        assertEquals(createdGame.getStatus(), GameStatus.CREATED);
        assertEquals(createdGame.getUserIds().size(), 1);
        assertNull(createdGame.getChosenWord());
    }

    @Test
    public void startGame_moreThanThreeUsers() throws Exception {
        Long userId1 = (long)1;
        Long userId2 = (long)2;
        Long userId3 = (long)3;
        Long userId4 = (long)4;

        testGame.setCurrentUserId(userId1);
        Game createdGame = gameService.createGame(testGame);
        Long gameId = createdGame.getId();

        Game putGame = new Game();
        putGame.setCurrentUserId(userId2);
        Game updatedGame = gameService.addUserToGame(gameId, putGame);

        putGame.setCurrentUserId(userId3);
        updatedGame = gameService.addUserToGame(gameId, putGame);

        putGame.setCurrentUserId(userId4);
        updatedGame = gameService.addUserToGame(gameId, putGame);

        updatedGame = gameService.startGame(gameId);

        assertEquals(updatedGame.getStatus(), GameStatus.RUNNING);
        assertTrue(updatedGame.getNormalMode());
    }

    @Test
    public void startGame_threeUsers() throws Exception {
        Long userId1 = (long)1;
        Long userId2 = (long)2;
        Long userId3 = (long)3;

        testGame.setCurrentUserId(userId1);
        Game createdGame = gameService.createGame(testGame);
        Long gameId = createdGame.getId();

        Game putGame = new Game();
        putGame.setCurrentUserId(userId2);
        Game updatedGame = gameService.addUserToGame(gameId, putGame);

        putGame.setCurrentUserId(userId3);
        updatedGame = gameService.addUserToGame(gameId, putGame);

        updatedGame = gameService.startGame(gameId);

        assertEquals(updatedGame.getStatus(), GameStatus.RUNNING);
        assertFalse(updatedGame.getNormalMode());
    }

    @Test
    public void addUserToGame_success() throws Exception {
        Long userId1 = (long)(1);
        testGame.setCurrentUserId(userId1);
        Game createdGame = gameService.createGame(testGame);

        Game putGame = new Game();
        Long userId2 = (long)(2);
        putGame.setCurrentUserId((userId2));
        Game updatedGame = gameService.addUserToGame(createdGame.getId(), putGame);

        assertEquals(updatedGame.getUserIds().size(), 2);
        assertEquals(updatedGame.getUserIds().get(0), userId1);
        assertEquals(updatedGame.getUserIds().get(1), userId2);
    }

    @Test
    public void removeUserFromGame_success() throws Exception {
        Long userId1 = (long)(1);
        testGame.setCurrentUserId(userId1);
        Game createdGame = gameService.createGame(testGame);

        Game putGame = new Game();
        Long userId2 = (long)(2);
        putGame.setCurrentUserId(userId2);
        Game updatedGame = gameService.addUserToGame(createdGame.getId(), putGame);

        updatedGame = gameService.removeUserFromGame(updatedGame.getId(), putGame);

        assertEquals(updatedGame.getUserIds().size(), 1);
        assertEquals(updatedGame.getUserIds().get(0), userId1);
    }



    @Test
    public void findGameById_success() throws Exception {
        testGame.setCurrentUserId((long)1);

        Game createdGame = gameService.createGame(testGame);
        Game gameById = gameService.getGameById(createdGame.getId());

        assertEquals(createdGame.getId(), gameById.getId());
    }

    @Test
    public void setChosenWord() throws Exception {
        String chosenWord = "TestWord";
        testGame.setCurrentUserId((long)1);
        Game createdGame = gameService.createGame(testGame);
        Long gameId = createdGame.getId();
        gameService.getActiveCard(gameId);
        Game updatedGame = gameService.setChosenWord(gameId, chosenWord);

        assertEquals(updatedGame.getChosenWord(), chosenWord);
    }

    @Test
    public void getActiveCard_success() throws Exception {
        testGame.setCurrentUserId((long)1);

        Game createdGame = gameService.createGame(testGame);
        gameService.getActiveCard(createdGame.getId());
    }

    @Test
    public void addClueToGame_success() throws Exception {
        String chosenWord = "TestWord";
        Clue newClue = new Clue();
        String clue = "TestClue";

        newClue.setClue(clue);
        testGame.setCurrentUserId((long)1);
        Game createdGame = gameService.createGame(testGame);
        Long gameId = createdGame.getId();
        gameService.getActiveCard(gameId);
        Game updatedGame = gameService.setChosenWord(gameId, chosenWord);
        Clue testClue = gameService.addClueToGame(gameId, newClue);
        updatedGame = gameService.getGameById(gameId);

        assertEquals(testClue.getClue(), clue);
        assertEquals(testClue.getValid(), ClueStatus.VALID);
    }

    @Test
    public void addClueToGame_amountOfUsersIsEqualToClues() throws Exception {
        String chosenWord = "TestWord";
        Clue newClue = new Clue();
        String clue = "TestClue";

        newClue.setClue(clue);
        testGame.setCurrentUserId((long)1);
        Game createdGame = gameService.createGame(testGame);
        Long gameId = createdGame.getId();
        gameService.getActiveCard(gameId);
        Game updatedGame = gameService.setChosenWord(gameId, chosenWord);
        Clue testClue = gameService.addClueToGame(gameId, newClue);

        String exceptionMessage = "There are already as many clues as users! Therefore, this clue can't be added!";
        PostRequestException409 exception = assertThrows(PostRequestException409.class, () -> gameService.addClueToGame(gameId, newClue), exceptionMessage);

        assertEquals(exceptionMessage, exception.getMessage());
    }
}