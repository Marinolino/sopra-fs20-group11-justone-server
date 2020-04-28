package ch.uzh.ifi.seal.soprafs20.service;

import ch.uzh.ifi.seal.soprafs20.ClueChecker.ClueChecker;
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
    public void getUserIndex_LastElement(){
        Game testGame = new Game();
        Long userId1 = (long)1;
        Long userId2 = (long)2;
        Long userId3 = (long)3;

        testGame.addUserId(userId1);
        testGame.addUserId(userId2);
        testGame.addUserId(userId3);
        testGame.setCurrentUserId(userId3);

        assertEquals(gameService.getUserIndex(testGame), 0);
    }

    @Test
    public void getUserIndex_NotLastElement(){
        Game testGame = new Game();
        Long userId1 = (long)1;
        Long userId2 = (long)2;
        Long userId3 = (long)3;

        testGame.addUserId(userId1);
        testGame.addUserId(userId2);
        testGame.addUserId(userId3);
        testGame.setCurrentUserId(userId2);

        assertEquals(gameService.getUserIndex(testGame), 2);
    }
}