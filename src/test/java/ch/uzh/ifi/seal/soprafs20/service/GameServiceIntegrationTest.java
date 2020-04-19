package ch.uzh.ifi.seal.soprafs20.service;

import ch.uzh.ifi.seal.soprafs20.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs20.entity.Game.Card;
import ch.uzh.ifi.seal.soprafs20.entity.Game.Game;
import ch.uzh.ifi.seal.soprafs20.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebAppConfiguration
@SpringBootTest
class GameServiceIntegrationTest {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameService gameService;

    @BeforeEach
    public void setup() {
        gameRepository.deleteAll();
    }

    @Test
    public void createGame_success() throws Exception {
        Game testGame = new Game();
        testGame.setCurrentUserId((long)1);

        Game createdGame = gameService.createGame(testGame);

        assertEquals(createdGame.getScore(), 0);
        assertEquals(createdGame.getRound(), 0);
        assertEquals(createdGame.getStatus(), GameStatus.CREATED);
        assertEquals(createdGame.getUserIds().size(), 1);
    }

    @Test
    public void addUserToGame_success() throws Exception {
        Game testGame = new Game();
        testGame.setCurrentUserId((long)1);

        Game createdGame = gameService.createGame(testGame);
        createdGame.addUserId((long)2);

        assertEquals(createdGame.getUserIds().size(), 2);
    }

    @Test
    public void findGameById_success() throws Exception {
        Game testGame = new Game();
        testGame.setCurrentUserId((long)1);

        Game createdGame = gameService.createGame(testGame);
        Game gameById = gameService.getGameById(createdGame.getId());

        assertEquals(createdGame.getId(), gameById.getId());
    }

    @Test
    public void getActiveCard_success() throws Exception {
        Game newGame = new Game();
        newGame.setCurrentUserId((long)1);

        Game createdGame = gameService.createGame(newGame);

        Card activeCard = gameService.getActiveCard(createdGame.getId());
    }
}