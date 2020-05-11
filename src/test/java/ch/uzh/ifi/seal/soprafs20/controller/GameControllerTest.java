package ch.uzh.ifi.seal.soprafs20.controller;

import ch.uzh.ifi.seal.soprafs20.constant.ChosenWordStatus;
import ch.uzh.ifi.seal.soprafs20.constant.ClueStatus;
import ch.uzh.ifi.seal.soprafs20.entity.game.*;
import ch.uzh.ifi.seal.soprafs20.exceptions.SopraServiceException;
import ch.uzh.ifi.seal.soprafs20.rest.dto.*;
import ch.uzh.ifi.seal.soprafs20.service.GameService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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

    private Game testGame;

    @BeforeEach
    public void setUp(){
        testGame = new Game();
        testGame.setId((long)1);
    }

    @Test
    public void createGame_success() throws Exception {
        GamePostDTO gamePostDTO = new GamePostDTO();
        gamePostDTO.setCurrentUserId((long)1);

        given(gameService.createGame(Mockito.any())).willReturn(testGame);

        MockHttpServletRequestBuilder postRequest = post("/games")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(gamePostDTO));

        mockMvc.perform(postRequest).andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(testGame.getId().intValue())));

    }

    @Test
    public void getAllGames_success() throws Exception {
        Game testGame2 = new Game();
        testGame2.setId((long)2);
        List<Game> games = new ArrayList<>();
        games.add(testGame);
        games.add(testGame2);

        given(gameService.getGames()).willReturn(games);

        MockHttpServletRequestBuilder getRequest = get("/games").contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id", is(testGame.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].id", is(testGame2.getId().intValue())));
    }

    @Test
    public void getGameById_success() throws Exception {
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
    public void addUserToGame_success() throws Exception {
        GamePutDTO gamePutDTO = new GamePutDTO();
        gamePutDTO.setCurrentUserId((long)1);

        given(gameService.addUserToGame(Mockito.any(), Mockito.any())).willReturn(testGame);

        MockHttpServletRequestBuilder putRequest = put("/games/join/1").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(gamePutDTO));;

        mockMvc.perform(putRequest).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(testGame.getId().intValue())));
    }

    @Test
    public void removeUserFromGame_success() throws Exception {
        GamePutDTO gamePutDTO = new GamePutDTO();
        gamePutDTO.setCurrentUserId((long)1);

        given(gameService.removeUserFromGame(Mockito.any(), Mockito.any())).willReturn(testGame);

        MockHttpServletRequestBuilder putRequest = put("/games/leave/1").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(gamePutDTO));;

        mockMvc.perform(putRequest).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(testGame.getId().intValue())));
    }

    @Test
    public void startGame_success() throws Exception {
        given(gameService.startGame(Mockito.any())).willReturn(testGame);

        MockHttpServletRequestBuilder putRequest = put("/games/start/1").contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(putRequest).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(testGame.getId().intValue())));
    }

    @Test
    public void finishGame_success() throws Exception {
        given(gameService.finishGame(Mockito.any())).willReturn(testGame);

        MockHttpServletRequestBuilder putRequest = put("/games/finish/1").contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(putRequest).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(testGame.getId().intValue())));
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

        MockHttpServletRequestBuilder putRequest = put("/cards/1").contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(putRequest).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.words", hasSize(5)));
        ;
    }


    @Test
    public void setChosenWord_success() throws Exception {
        CardPutDTO cardPutDTO = new CardPutDTO();
        cardPutDTO.setChosenWord("Test3");

        given(gameService.updateChosenWord(Mockito.any(), Mockito.any())).willReturn(testGame);

        MockHttpServletRequestBuilder putRequest = put("/chosenword/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(cardPutDTO));

        mockMvc.perform(putRequest).andExpect(status().isOk());
    }

    @Test
    public void getChosenWord_success() throws Exception {
        testGame.setChosenWord("TestWord");
        testGame.setWordStatus(ChosenWordStatus.ACCEPTED);

        given(gameService.getGameById(Mockito.any())).willReturn(testGame);

        MockHttpServletRequestBuilder getRequest = get("/chosenword/1")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.chosenWord", is("TestWord")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.wordStatus", is(ChosenWordStatus.ACCEPTED.toString())));
    }

    @Test
    public void updateChosenWord_reject() throws Exception {
        ChosenWordPutDTO chosenWordPutDTO = new ChosenWordPutDTO();
        chosenWordPutDTO.setStatus(false);
        testGame.setWordStatus(ChosenWordStatus.REJECTED);

        given(gameService.updateChosenWord(Mockito.any(), Mockito.any())).willReturn(testGame);

        MockHttpServletRequestBuilder putRequest = put("/chosenword/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(chosenWordPutDTO));

        mockMvc.perform(putRequest).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.wordStatus", is(ChosenWordStatus.REJECTED.toString())));

    }

    @Test
    public void createClue_validClue() throws Exception {
        CluePostDTO cluePostDTO = new CluePostDTO();
        cluePostDTO.setClueWord("TestClue");
        cluePostDTO.setTime(10);
        Clue testClue = new Clue();
        testClue.setClueWord("TestClue");
        testClue.setValid(ClueStatus.VALID);
        testClue.setTime(10);

        given(gameService.addClueToGame(Mockito.any(), Mockito.any())).willReturn(testClue);

        MockHttpServletRequestBuilder postRequest = post("/clues/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(cluePostDTO));

        mockMvc.perform(postRequest).andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.clueWord", is(cluePostDTO.getClueWord())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.time", is(cluePostDTO.getTime())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valid", is(ClueStatus.VALID.toString())));
    }

    @Test
    public void getAllClues_success() throws Exception {
        Long userId1 = (long)1;
        testGame.addUserId(userId1);

        Long userId2 = (long)2;
        testGame.addUserId(userId2);

        Long userId3 = (long)3;
        testGame.addUserId(userId3);

        List<Clue> clues = new ArrayList<>();

        Clue testClue1 = new Clue();
        testClue1.setClueWord("TestClue");
        testClue1.setValid(ClueStatus.VALID);
        testClue1.setTime(10);
        clues.add(testClue1);

        Clue testClue2 = new Clue();
        testClue2.setClueWord("AnotherTestClue");
        testClue2.setValid(ClueStatus.DUPLICATE);
        testClue2.setTime(5);
        clues.add(testClue2);

        testGame.setClues(clues);

        given(gameService.getGameById(Mockito.any())).willReturn(testGame);

        MockHttpServletRequestBuilder getRequest = get("/clues/1")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.clues", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.clues[0]", is(testClue1.getClueWord())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.allAutomaticClues", is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.allManualClues", is(false)));
    }

    @Test
    public void setCluesToInvalid_success() throws Exception {
        CluePutDTO cluePutDTO = new CluePutDTO();
        cluePutDTO.setCluesToChange(Arrays.asList("TestClue", "AnotherTestClue"));

        List<Clue> clues = new ArrayList<>();

        Clue testClue1 = new Clue();
        testClue1.setClueWord("TestClue");
        testClue1.setValid(ClueStatus.INVALID);
        testClue1.setTime(10);
        clues.add(testClue1);

        Clue testClue2 = new Clue();
        testClue2.setClueWord("AnotherTestClue");
        testClue2.setValid(ClueStatus.INVALID);
        testClue2.setTime(5);
        clues.add(testClue2);

        testGame.setClues(clues);

        given(gameService.setCluesToInvalid(Mockito.any(), Mockito.any())).willReturn(testGame);

        MockHttpServletRequestBuilder putRequest = put("/clues/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(cluePutDTO));

        mockMvc.perform(putRequest).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.clues", hasSize(0)));
    }

    @Test
    public void skipGuessing() throws Exception {
        MockHttpServletRequestBuilder putRequest = put("/skip/1")
                .contentType(MediaType.APPLICATION_JSON);

        given(gameService.skipGuessing(Mockito.any())).willReturn(testGame);

        mockMvc.perform(putRequest).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(testGame.getId().intValue())));
    }

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