package ch.uzh.ifi.seal.soprafs20.service;

import ch.uzh.ifi.seal.soprafs20.entity.Game.Card;
import ch.uzh.ifi.seal.soprafs20.entity.Game.Game;
import ch.uzh.ifi.seal.soprafs20.entity.User;
import ch.uzh.ifi.seal.soprafs20.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.FileNotFoundException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameService gameService;

    private Game testGame;
    User testUser1;
    User testUser2;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        //Mockito.when(gameRepository.save(Mockito.any())).thenReturn(testGame);
    }

    @Test
    void createCards_13Cards() throws Exception {
        List<Card> cardList = gameService.createCards();

        assertEquals(cardList.size(), 13);
    }

    @Test
    void createCards_5WordsPerCard() throws FileNotFoundException {
        List<Card> cardList = gameService.createCards();

        for (Card card : cardList){
            assertEquals(card.getWords().size(), 5);
        }
    }
}