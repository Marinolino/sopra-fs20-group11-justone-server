package ch.uzh.ifi.seal.soprafs20.controller;

import ch.uzh.ifi.seal.soprafs20.entity.Game.*;
import ch.uzh.ifi.seal.soprafs20.entity.User;
import ch.uzh.ifi.seal.soprafs20.exceptions.SopraServiceException;
import ch.uzh.ifi.seal.soprafs20.rest.dto.CardPutDTO;
import ch.uzh.ifi.seal.soprafs20.rest.dto.ClueGetDTO;
import ch.uzh.ifi.seal.soprafs20.rest.dto.CluePostDTO;
import ch.uzh.ifi.seal.soprafs20.service.GameService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.hamcrest.Matchers.*;


@WebMvcTest(GameController.class)
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @Test
    public void getGameById_success() throws Exception {
        Game testGame = new Game();
        testGame.setId((long)1);

        given(gameService.getGameById(Mockito.any())).willReturn(testGame);

        MockHttpServletRequestBuilder getRequest = get("/games/1").contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(testGame.getId().intValue())));
    }

    @Test
    public void getGameById_noGameFound() throws Exception {
        given(gameService.getGameById(Mockito.any())).willReturn(null);

        MockHttpServletRequestBuilder getRequest = get("/games/1").contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest).andExpect(status().isNotFound());
    }

    @Test
    public void getActiveCard_success() throws Exception{
        List<String> wordList = new ArrayList<>();
        Card testCard = new Card();

        wordList.add("Test1");
        wordList.add("Test2");
        wordList.add("Test3");
        wordList.add("Test4");
        wordList.add("Test5");
        testCard.setMysteryWords(wordList);

        given(gameService.getActiveCard(Mockito.any())).willReturn(testCard);

        MockHttpServletRequestBuilder getRequest = get("/cards/1").contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.words", hasSize(5)));
        ;
    }


    @Test
    public void setChosenWord_success() throws Exception {
        Game testGame = new Game();

        CardPutDTO cardPutDTO = new CardPutDTO();
        cardPutDTO.setChosenWord("Test3");

        MockHttpServletRequestBuilder putRequest = put("/chosenword/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(cardPutDTO));

        given(gameService.getGameById(Mockito.any())).willReturn(testGame);

        mockMvc.perform(putRequest).andExpect(status().isOk());
    }

    /*@Disabled("Not implemented yet")
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