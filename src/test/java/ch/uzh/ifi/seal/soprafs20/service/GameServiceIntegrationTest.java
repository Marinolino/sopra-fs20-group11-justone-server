package ch.uzh.ifi.seal.soprafs20.service;

import ch.uzh.ifi.seal.soprafs20.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs20.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs20.entity.Game.Game;
import ch.uzh.ifi.seal.soprafs20.entity.User;
import ch.uzh.ifi.seal.soprafs20.exceptions.API.GET.GetRequestException404;
import ch.uzh.ifi.seal.soprafs20.exceptions.API.POST.PostRequestException409;
import ch.uzh.ifi.seal.soprafs20.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs20.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebAppConfiguration
@SpringBootTest
class GameServiceIntegrationTest {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameService gameService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void setup() {
        gameRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void createGame_success() throws Exception {
        List<Long> testUserList = new ArrayList<>();
        testUserList.add((long)1);
        testUserList.add((long)2);

        Game testGame = new Game();
        testGame.setUserIds(testUserList);
        testGame.setNormalMode(true);

        Game createdGame = gameService.createGame(testGame);

        assertEquals(createdGame.getScore(), 0);
        assertEquals(createdGame.getRound(), 1);
        assertEquals(createdGame.getStatus(), GameStatus.RUNNING);
        assertEquals(createdGame.getUserIds().size(), 2);
    }

    @Test
    public void findGameById_success() throws Exception {
        List<Long> testUserList = new ArrayList<>();
        testUserList.add((long)1);
        testUserList.add((long)2);

        Game testGame = new Game();
        testGame.setUserIds(testUserList);
        testGame.setNormalMode(true);

        Game createdGame = gameService.createGame(testGame);
        Game gameById = gameService.getGameById(createdGame.getId());

        assertEquals(createdGame.getId(), gameById.getId());
    }
}