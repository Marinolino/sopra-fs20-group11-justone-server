package ch.uzh.ifi.seal.soprafs20.controller;

import ch.uzh.ifi.seal.soprafs20.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs20.entity.User;
import ch.uzh.ifi.seal.soprafs20.exceptions.SopraServiceException;
import ch.uzh.ifi.seal.soprafs20.rest.dto.UserPostDTO;
import ch.uzh.ifi.seal.soprafs20.entity.Game.Game;
import ch.uzh.ifi.seal.soprafs20.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * UserControllerTest
 * This is a WebMvcTest which allows to test the UserController i.e. GET/POST request without actually sending them over the network.
 * This tests if the UserController works.
 */
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    private Game gameService;

    @Test
    public void givenUsers_whenGetUsers_thenReturnJsonArray() throws Exception {
        // given
        User user = new User();
        user.setName("Firstname Lastname");
        user.setUsername("firstname@lastname");
        user.setPassword("testPassword");
        user.setStatus(UserStatus.OFFLINE);

        List<User> allUsers = Collections.singletonList(user);

        // this mocks the UserService -> we define above what the userService should return when getUsers() is called
        given(userService.getUsers()).willReturn(allUsers);

        // when
        MockHttpServletRequestBuilder getRequest = get("/users").contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(user.getName())))
                .andExpect(jsonPath("$[0].username", is(user.getUsername())))
                .andExpect(jsonPath("$[0].status", is(user.getStatus().toString())));
    }

    @Test
    public void createUser_validInput_userCreated() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("TestUser");
        user.setUsername("testUsername");
        user.setToken("1");
        user.setPassword("TestPassword");
        user.setDate("27/02/2020");
        user.setStatus(UserStatus.ONLINE);
        user.setGamesPlayed(0);
        user.setScore(0);
        user.setInGame(false);

        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setName("TestUser");
        userPostDTO.setUsername("testUsername");
        userPostDTO.setPassword("testPassword");

        given(userService.createUser(Mockito.any())).willReturn(user);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPostDTO));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.name", is(user.getName())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.status", is(user.getStatus().toString())))
                .andExpect(jsonPath("$.score", is(user.getScore())))
                .andExpect(jsonPath("$.inGame", is(user.getInGame())))
                .andExpect(jsonPath("$.gamesPlayed", is(user.getGamesPlayed())));
    }
    
     @Test
    public void testGetUsers() throws Exception {
        // given
        User user1 = new User();
        user1.setId(42L);
        user1.setName("Mikko Muller");
        user1.setUsername("mikmu");
        user1.setStatus(UserStatus.OFFLINE);

        User user2 = new User();
        user2.setId(24L);
        user2.setName("Nikko Nuller");
        user2.setUsername("niknu");
        user2.setStatus(UserStatus.OFFLINE);

        List<User> users = new ArrayList();
        users.add(user1);
        users.add(user2);

        given(userService.getUsers()).willReturn(users);
        // when
        MockHttpServletRequestBuilder getRequest = get("/users").contentType(MediaType.APPLICATION_JSON);
        // then
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(users.get(0).getName())))
                .andExpect(jsonPath("$[0].username", is(users.get(0).getUsername())))
                .andExpect(jsonPath("$[0].id", is(42)))

                .andExpect(jsonPath("$[1].name", is(users.get(1).getName())))
                .andExpect(jsonPath("$[1].username", is(users.get(1).getUsername())))
                .andExpect(jsonPath("$[1].id", is(24)));
    }

    /*@Disabled("Not implemented yet")
    @Test
    public void getNextCard_from_Deck_success() throws Exception {
        List<String> mysteryWords = Arrays.asList("WordOne", "WordTwo", "WordThree", "WordFour", "WordFive");
        Card card = new Card(mysteryWords);
        GameGetDTO gameGetDTO = new GameGetDTO();

        given(gameService.getNextCard()).willReturn(card);

        MockHttpServletRequestBuilder getRequest = get("/cards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(gameGetDTO));

        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$.worldList[0]", is(mysteryWords.get(0))))
                .andExpect(jsonPath("$.worldList[1]", is(mysteryWords.get(1))))
                .andExpect(jsonPath("$.worldList[2]", is(mysteryWords.get(2))))
                .andExpect(jsonPath("$.worldList[3]", is(mysteryWords.get(3))))
                .andExpect(jsonPath("$.worldList[4]", is(mysteryWords.get(4))));
    }

    @Disabled("Not implemented yet")
    @Test
    public void setChosenWord() throws Exception {
        List<String> mysteryWords = Arrays.asList("MysteryWord", "WordTwo", "WordThree", "WordFour", "WordFive");
        Card card = new Card(mysteryWords);
        GameService.deck.setActiveCard(card);
        GamePutDTO gamePutDTO = new GamePutDTO();

        MockHttpServletRequestBuilder putRequest = put("/cards/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(gamePutDTO));

        mockMvc.perform(putRequest).andExpect(status().isOk());
    }

    @Disabled("Not implemented yet")
    @Test
    public void getClues_success() throws Exception {
        List<String> clues = Arrays.asList("clue1", "clue2", "clue3", "clue4");
        GameGetDTO gameGetDTO = new GameGetDTO();

        MockHttpServletRequestBuilder getRequest = get("/clues")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(gameGetDTO));

        given(GameService.getClues(Mockito.any()).willReturn(clues));

        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$.clues[0]", is(clues.get(0))))
                .andExpect(jsonPath("$.clues[1]", is(clues.get(1))))
                .andExpect(jsonPath("$.clues[2]", is(clues.get(2))))
                .andExpect(jsonPath("$.clues[3]", is(clues.get(3))));
    }

    @Disabled("Not implemented yet")
    @Test
    public void checkGuess_correctGuess() throws Exception {
        String guess = "MyGuess";
        GamePostDTO gamePostDTO = new GamePostDTO();
        gamePostDTO.setGuess(guess);

        given(GameService.getChosenWord(Mockito.any()).willReturn("MyGuess"));

        MockHttpServletRequestBuilder postRequest = post("/guess")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(gamePostDTO));

        mockMvc.perform(postRequest).andExpect(status().isOk());
    }

    @Disabled("Not implemented yet")
    @Test
    public void getScore() throws Exception {
        Score scoreMessage = new Score(12, "Incredible! Your friends must be impressed!");
        GameGetDTO gameGetDTO = new GameGetDTO();

        MockHttpServletRequestBuilder getRequest = get("/score")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(gameGetDTO));

        given(GameService.getScore(Mockito.any()).willReturn(scoreMessage));

        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$.score", is(scoreMessage.getScore())))
                .andExpect(jsonPath("$.message", is(scoreMessage.getMessage())));
    }
    */

    /**
     * Helper Method to convert userPostDTO into a JSON string such that the input can be processed
     * Input will look like this: {"name": "Test User", "username": "testUsername"}
     * @param object
     * @return string
     */
    private String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            throw new SopraServiceException(String.format("The request body could not be created.%s", e.toString()));
        }
    }
}
