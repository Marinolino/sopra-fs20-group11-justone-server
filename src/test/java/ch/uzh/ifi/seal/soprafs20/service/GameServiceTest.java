package ch.uzh.ifi.seal.soprafs20.service;

import ch.uzh.ifi.seal.soprafs20.constant.ChosenWordStatus;
import ch.uzh.ifi.seal.soprafs20.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs20.entity.Game.Card;
import ch.uzh.ifi.seal.soprafs20.entity.Game.Game;
import ch.uzh.ifi.seal.soprafs20.exceptions.API.PUT.PutRequestException409;
import ch.uzh.ifi.seal.soprafs20.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs20.rest.dto.ChosenWordPutDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameService gameService;

    Game testGame;
    Long gameId = (long)1;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        testGame = new Game();
        testGame.setCurrentUserId((long)1);
        testGame.setId(gameId);
    }

    @Test
    public void createCards_13Cards() throws Exception {
        List<Card> cardList = gameService.createCards();

        assertEquals(13, cardList.size());
    }

    @Test
    public void createCards_5WordsPerCard() throws FileNotFoundException {
        List<Card> cardList = gameService.createCards();

        for (Card card : cardList){
            assertEquals(5, card.getMysteryWords().size());
        }
    }

    @Test
    public void getUserIndex_LastElement(){
        Long userId1 = (long)1;
        Long userId2 = (long)2;
        Long userId3 = (long)3;

        testGame.addUserId(userId1);
        testGame.addUserId(userId2);
        testGame.addUserId(userId3);
        testGame.setCurrentUserId(userId3);

        assertEquals(0, gameService.getUserIndex(testGame));
    }

    @Test
    public void getUserIndex_NotLastElement(){
        Long userId1 = (long)1;
        Long userId2 = (long)2;
        Long userId3 = (long)3;

        testGame.addUserId(userId1);
        testGame.addUserId(userId2);
        testGame.addUserId(userId3);
        testGame.setCurrentUserId(userId2);

        assertEquals(2, gameService.getUserIndex(testGame));
    }

    @Test
    public void startGame_gameAlreadyStarted() throws Exception {
        testGame.setStatus(GameStatus.FINISHED);

        Mockito.when(gameRepository.findById(Mockito.any())).thenReturn(java.util.Optional.ofNullable(testGame));

        String exceptionMessage = "This game has already started or finished!";
        PutRequestException409 exception = assertThrows(PutRequestException409.class, () -> gameService.startGame(gameId), exceptionMessage);

        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    public void finishGame_gameNotRunning() throws Exception {
        testGame.setStatus(GameStatus.CREATED);

        Mockito.when(gameRepository.findById(Mockito.any())).thenReturn(java.util.Optional.ofNullable(testGame));

        String exceptionMessage = "This game is currently not running!";
        PutRequestException409 exception = assertThrows(PutRequestException409.class, () -> gameService.finishGame(gameId), exceptionMessage);

        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    public void updateChosenWord_WordAlreadyChanged() throws Exception {
        ChosenWordPutDTO chosenWordPutDTO = new ChosenWordPutDTO();
        testGame.setChangeWord(false);

        Mockito.when(gameRepository.findById(Mockito.any())).thenReturn(java.util.Optional.ofNullable(testGame));

        String exceptionMessage = "You can only reject one word per turn!";
        PutRequestException409 exception = assertThrows(PutRequestException409.class, () -> gameService.updateChosenWord(gameId, chosenWordPutDTO), exceptionMessage);

        assertEquals(exceptionMessage, exception.getMessage());
    }
}