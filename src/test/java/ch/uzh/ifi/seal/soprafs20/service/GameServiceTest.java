package ch.uzh.ifi.seal.soprafs20.service;

import ch.uzh.ifi.seal.soprafs20.entity.Game.Card;
import ch.uzh.ifi.seal.soprafs20.entity.Game.Game;
import ch.uzh.ifi.seal.soprafs20.exceptions.API.POST.PostRequestException409;
import ch.uzh.ifi.seal.soprafs20.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameService gameService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createCards_13Cards() throws Exception {
        List<Card> cardList = gameService.createCards();

        assertEquals(cardList.size(), 13);
    }

    @Test
    public void createCards_5WordsPerCard() throws FileNotFoundException {
        List<Card> cardList = gameService.createCards();

        for (Card card : cardList){
            assertEquals(card.getMysteryWords().size(), 5);
        }
    }

    @Test
    public void checkIfClueInputIsValid_ClueIsBlank() throws Exception{
        String exceptionMessage = "Clues must not be empty!";

        String clue1 = "";
        PostRequestException409 exception1 = assertThrows(PostRequestException409.class, () -> gameService.checkIfClueInputIsValid(clue1), exceptionMessage);
        assertEquals(exceptionMessage, exception1.getMessage());

        String clue2 = null;
        PostRequestException409 exception2 = assertThrows(PostRequestException409.class, () -> gameService.checkIfClueInputIsValid(clue2), exceptionMessage);
        assertEquals(exceptionMessage, exception2.getMessage());
    }

    @Test
    public void checkIfClueInputIsValid_ClueContainsWhiteSpaces() throws Exception{
        String exceptionMessage = "Clues must not contain any whitespaces!";

        String clue1 = " TestClue";
        PostRequestException409 exception1 = assertThrows(PostRequestException409.class, () -> gameService.checkIfClueInputIsValid(clue1), exceptionMessage);
        assertEquals(exceptionMessage, exception1.getMessage());

        String clue2 = "TestClue ";
        PostRequestException409 exception2 = assertThrows(PostRequestException409.class, () -> gameService.checkIfClueInputIsValid(clue2), exceptionMessage);
        assertEquals(exceptionMessage, exception2.getMessage());

        String clue3 = "Test Clue";
        PostRequestException409 exception3 = assertThrows(PostRequestException409.class, () -> gameService.checkIfClueInputIsValid(clue3), exceptionMessage);
        assertEquals(exceptionMessage, exception2.getMessage());
    }


}