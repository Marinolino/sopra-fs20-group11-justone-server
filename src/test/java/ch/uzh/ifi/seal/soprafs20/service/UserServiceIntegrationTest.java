package ch.uzh.ifi.seal.soprafs20.service;

import ch.uzh.ifi.seal.soprafs20.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs20.entity.User;
import ch.uzh.ifi.seal.soprafs20.exceptions.api.post.PostRequestException409;
import ch.uzh.ifi.seal.soprafs20.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the UserResource REST resource.
 *
 * @see UserService
 */
@WebAppConfiguration
@SpringBootTest
public class UserServiceIntegrationTest {

    User testUser;

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void setup() {
        testUser = new User();
        testUser.setName("testuser");
        testUser.setUsername("testuser");
        testUser.setPassword("testpwd");
        userRepository.deleteAll();
    }

    @Test
    public void createUser_validInputs_success() {
        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("testPassword");

        // when
        User createdUser = userService.createUser(testUser);

        // then
        assertEquals(testUser.getId(), createdUser.getId());
        assertEquals(testUser.getName(), createdUser.getName());
        assertEquals(testUser.getUsername(), createdUser.getUsername());
        assertNotNull(createdUser.getToken());
        assertEquals(UserStatus.OFFLINE, createdUser.getStatus());
    }


    @Test
    public void createUser_duplicateUsername_throwsException() {
        assertNull(userRepository.findByUsername("testUsername"));

        User testUser = new User();
        testUser.setName("testName");
        testUser.setUsername("testUsername");
        testUser.setPassword("testPassword");
        User createdUser = userService.createUser(testUser);

        // attempt to create second user with same username
        User testUser2 = new User();
        testUser2.setName("testName2");
        testUser2.setUsername("testUsername");
        testUser2.setPassword("testPassword");

        // check that an error is thrown
        String exceptionMessage = String.format("There is already a user '%s'! Please try again!", testUser.getUsername());
        PostRequestException409 exception = assertThrows(PostRequestException409.class, () -> userService.createUser(testUser2), exceptionMessage);
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    @Transactional
    void joinGame() throws Exception {
        userService.createUser(testUser);

        assertNotNull(testUser.getId());
        Long userId = testUser.getId();

        User gameUser = userService.joinGame(userId);
        assertEquals(UserStatus.INGAME, gameUser.getStatus());
    }

    @Test
    @Transactional
    void leaveGame() {
        userService.createUser(testUser);

        assertNotNull(testUser.getId());
        Long userId = testUser.getId();

        userService.joinGame(userId);
        assertEquals(UserStatus.INGAME, testUser.getStatus());

        userService.leaveGame(userId);
        assertEquals(UserStatus.ONLINE, testUser.getStatus());
    }

    @Test
    @Transactional
    void updateUserGameStats() {
        userService.createUser(testUser);

        assertNotNull(testUser.getId());
        Long userId = testUser.getId();

        User userInput = new User();
        userInput.setId(userId);
        userInput.setDuplicateClues(1);
        userInput.setCorrectlyGuessed(4);

        userService.updateUserGameStats(userId, userInput);
        assertEquals(testUser.getDuplicateClues(), userInput.getDuplicateClues());
        assertEquals(testUser.getCorrectlyGuessed(), userInput.getCorrectlyGuessed());
    }

    @Test
    @Transactional
    void updateUserScore() {
        userService.createUser(testUser);

        assertNotNull(testUser.getId());
        Long userId = testUser.getId();

        User userInput = new User();
        userInput.setId(userId);
        userInput.setDuplicateClues(1);
        userInput.setCorrectlyGuessed(4);

        userService.updateUserGameStats(userId, userInput);

        userInput.setScore(6);

        userService.updateUserScore(userId, userInput);
        assertEquals(9, testUser.getScore());
        assertEquals(userInput.getDuplicateClues(), testUser.getDuplicateClues());
        assertEquals(userInput.getCorrectlyGuessed(), testUser.getCorrectlyGuessed());
    }

    @Test
    @Transactional
    void logIn() {
        userService.createUser(testUser);

        assertNotNull(testUser.getId());

        userService.logIn(testUser);

        assertNotNull(testUser.getToken());
        assertEquals(UserStatus.ONLINE, testUser.getStatus());
    }

    @Test
    @Transactional
    void logOut() {
        userService.createUser(testUser);

        assertNotNull(testUser.getId());

        userService.logIn(testUser);
        assertNotNull(testUser.getToken());
        assertEquals(UserStatus.ONLINE, testUser.getStatus());

        userService.logOut(testUser.getId());
        assertEquals(UserStatus.OFFLINE, testUser.getStatus());
    }
}
