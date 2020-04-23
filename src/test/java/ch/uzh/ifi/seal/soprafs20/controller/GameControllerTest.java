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
        Deck testDeck = new Deck();
        testDeck.setCardList(createCards());

        Game testGame = new Game();
        testGame.setId((long)1);
        testGame.setDeck(testDeck);

        given(gameService.getActiveCard(Mockito.any())).willReturn(testDeck.getTopCard());

        MockHttpServletRequestBuilder getRequest = get("/cards/1").contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.words", notNullValue()));
        ;
    }


    @Test
    public void setChosenWord_success() throws Exception {
        List<String> wordList = new ArrayList<>();
        Card testCard = new Card();

        wordList.add("Test1");
        wordList.add("Test2");
        wordList.add("Test3");
        wordList.add("Test4");
        wordList.add("Test5");
        testCard.setMysteryWords(wordList);

        Game testGame = new Game();
        testGame.setActiveCard(testCard);

        CardPutDTO cardPutDTO = new CardPutDTO();
        cardPutDTO.setChosenWord("Test3");


        MockHttpServletRequestBuilder putRequest = put("/cards/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(cardPutDTO));

        given(gameService.getGameById(Mockito.any())).willReturn(testGame);

        mockMvc.perform(putRequest).andExpect(status().isOk());
    }
    //TODO: Move these tests to integration
    /*@Test
    public void postClue_success() throws Exception {
        Game testGame = new Game();
        Card testCard = createCards().get(0);
        testGame.setActiveCard(testCard);
        testGame.setChosenWord(testCard.getWordList().get(2).getId());
        Clue clue1 = new Clue();
        CluePostDTO cluePostDTO = new CluePostDTO();

        cluePostDTO.setClue("A");
        clue1.setClue("A");
        testGame.addClue(clue1);

        MockHttpServletRequestBuilder postRequest = post("/clues/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(cluePostDTO));

        given(gameService.addClueToGame(Mockito.any(), Mockito.any())).willReturn(testGame);

        mockMvc.perform(postRequest).andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.clues[0]", is(clue1.getClue())));
    }

    @Test
    public void getClues_success() throws Exception {
        Game testGame = new Game();
        MysteryWord testWord = new MysteryWord();
        testWord.setWord("C");
        Clue clue1 = new Clue();
        Clue clue2 = new Clue();

        clue1.setClue("A");
        clue2.setClue("B");
        testGame.addClue(clue1);
        testGame.addClue(clue2);

        MockHttpServletRequestBuilder getRequest = get("/clues/1")
                .contentType(MediaType.APPLICATION_JSON);

        given(gameService.getGameById(Mockito.any())).willReturn(testGame);

        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.clues[0]", is(clue1.getClue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.clues[1]", is(clue2.getClue())));
    }*/

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

    //creates 13 random cards, each containing 5 random words
    public List<Card> createCards() throws FileNotFoundException {

        List<String> wordList = new ArrayList<>();
        List<Card> cardList = new ArrayList<>();

        //create the Mystery Words and set their attributes
        try (Scanner s = new Scanner(new FileReader("src/main/resources/JustOneWordsEN.txt"))) {
            while (s.hasNext()) {
                wordList.add(s.nextLine());
            }
        }
        //create the cards
        Collections.shuffle(wordList);
        for (int i = 0; i<13; i++){
            List<String> wordsOnCard = new ArrayList<>();
            for (int j = 0; j<5; j++ ){
                wordsOnCard.add(wordList.remove(0));
            }
            Card newCard = new Card();
            newCard.setMysteryWords(wordsOnCard);
            cardList.add(newCard);
        }
        return cardList;
    }
}