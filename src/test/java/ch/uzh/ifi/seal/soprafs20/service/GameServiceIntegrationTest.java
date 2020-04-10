package ch.uzh.ifi.seal.soprafs20.service;

import ch.uzh.ifi.seal.soprafs20.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs20.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs20.entity.Game.Game;
import ch.uzh.ifi.seal.soprafs20.entity.User;
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
    public void createGame_success() throws FileNotFoundException {
        User testUser1 = new User();
        testUser1.setName("testName");
        testUser1.setUsername("testUsername");
        testUser1.setPassword("testPassword");
        testUser1.setDate(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));

        testUser1 = userService.createUser(testUser1);

        User testUser2 = new User();
        testUser2.setName("testName");
        testUser2.setUsername("testUsername2");
        testUser2.setPassword("testPassword");
        testUser2.setDate(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));

        testUser2 = userService.createUser(testUser2);

        List<User> TestUserList = new ArrayList<>();
        TestUserList.add(testUser1);
        TestUserList.add(testUser2);

        Game testGame = new Game();
        testGame.setUsers(TestUserList);
        testGame.setNormalMode(true);

        Game createdGame = gameService.createGame(testGame);

        assertEquals(createdGame.getScore(), 0);
        assertEquals(createdGame.getStatus(), GameStatus.RUNNING);
        assertEquals(createdGame.getUsers().size(), 2);
    }

}