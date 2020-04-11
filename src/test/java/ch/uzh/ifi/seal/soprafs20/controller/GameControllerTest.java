package ch.uzh.ifi.seal.soprafs20.controller;

import ch.uzh.ifi.seal.soprafs20.entity.Game.Game;
import ch.uzh.ifi.seal.soprafs20.exceptions.SopraServiceException;
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

    /*@Disabled("Not implemented yet")
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