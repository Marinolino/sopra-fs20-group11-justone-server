package ch.uzh.ifi.seal.soprafs20.service;

import ch.uzh.ifi.seal.soprafs20.constant.ChosenWordStatus;
import ch.uzh.ifi.seal.soprafs20.constant.ClueStatus;
import ch.uzh.ifi.seal.soprafs20.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs20.entity.game.Card;
import ch.uzh.ifi.seal.soprafs20.entity.game.Clue;
import ch.uzh.ifi.seal.soprafs20.entity.game.Game;
import ch.uzh.ifi.seal.soprafs20.entity.game.Guess;
import ch.uzh.ifi.seal.soprafs20.exceptions.api.post.PostRequestException409;
import ch.uzh.ifi.seal.soprafs20.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs20.rest.dto.ChosenWordPutDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebAppConfiguration
@SpringBootTest
class GameServiceIntegrationTest {

    Game testGame;
    Game createdGame;
    Long gameId;

    @Qualifier("gameRepository")
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameService gameService;


    @BeforeEach
    public void setup() throws FileNotFoundException {
        gameRepository.deleteAll();
        testGame = new Game();
        testGame.setCurrentUserId((long)1);
        createdGame = gameService.createGame(testGame);
        gameId = createdGame.getId();
    }

    @Test
    public void getAllGames_noGamesInRepo(){
        gameRepository.deleteAll();
        List<Game> games = gameService.getGames();

        assertEquals(0, games.size());
    }

    @Test
    public void createGame_success() {
        assertEquals(0, createdGame.getScore());
        assertEquals(1, createdGame.getRound());
        assertEquals(GameStatus.CREATED, createdGame.getStatus());
        assertEquals(1, createdGame.getUserIds().size());
        assertNull(createdGame.getChosenWord());
    }

    @Test
    public void startGame_moreThanThreeUsers() throws Exception {
        Long userId2 = (long)2;
        Long userId3 = (long)3;
        Long userId4 = (long)4;

        Game updatedGame = gameService.addUserToGame(gameId, userId2);
        updatedGame = gameService.addUserToGame(gameId, userId3);
        updatedGame = gameService.addUserToGame(gameId, userId4);

        updatedGame = gameService.startGame(gameId);

        assertEquals(GameStatus.RUNNING, updatedGame.getStatus());
        assertTrue(updatedGame.getNormalMode());
    }

    @Test
    public void startGame_threeUsers() {
        Long userId2 = (long)2;
        Long userId3 = (long)3;

        Game updatedGame = gameService.addUserToGame(gameId, userId2);

        updatedGame = gameService.addUserToGame(gameId, userId3);

        updatedGame = gameService.startGame(gameId);

        assertEquals(GameStatus.RUNNING, updatedGame.getStatus());
        assertFalse(updatedGame.getNormalMode());
    }

    @Test
    public void addUserToGame_success() {
        Long userId1 = (long)(1);
        Long userId2 = (long)(2);
        Game updatedGame = gameService.addUserToGame(createdGame.getId(), userId2);

        assertEquals(2, updatedGame.getUserIds().size());
        assertEquals(userId1, updatedGame.getUserIds().get(0));
        assertEquals(userId2, updatedGame.getUserIds().get(1));
    }

    @Test
    public void removeUserFromGame_success() {
        Long userId1 = (long)(1);
        Long userId2 = (long)(2);
        Game updatedGame = gameService.addUserToGame(gameId, userId2);

        updatedGame = gameService.removeUserFromGame(gameId, userId1);

        assertEquals(1, updatedGame.getUserIds().size());
        assertEquals(userId2, updatedGame.getUserIds().get(0));
    }



    @Test
    public void findGameById_success() throws Exception {
        Game gameById = gameService.getGameById(gameId);

        assertEquals(createdGame.getId(), gameById.getId());
    }

    @Test
    @Transactional
    public void setChosenWord() throws Exception {
        String chosenWord = getFirstWordOnActiveCard();
        Game updatedGame = gameService.setChosenWord(gameId, chosenWord);

        assertEquals(chosenWord, updatedGame.getChosenWord());
        assertEquals(ChosenWordStatus.SELECTED, updatedGame.getWordStatus());
    }

    @Test
    @Transactional
    public void addClueToGame_success() throws Exception {
        testGame.addUserId((long)2);
        Clue newClue = new Clue();
        String clue = "TestClue";

        newClue.setClueWord(clue);
        String chosenWord = getFirstWordOnActiveCard();
        gameService.setChosenWord(gameId, chosenWord);
        Clue testClue = gameService.addClueToGame(gameId, newClue);

        assertEquals(clue, testClue.getClueWord());
        assertEquals(ClueStatus.VALID, testClue.getValid());
    }

    @Test
    @Transactional
    public void addClueToGame_amountOfUsersIsEqualToClues() throws Exception {
        testGame.addUserId((long)2);
        Clue newClue = new Clue();
        String clue = "TestClue";
        newClue.setClueWord(clue);

        String chosenWord = getFirstWordOnActiveCard();
        gameService.setChosenWord(gameId, chosenWord);
        gameService.addClueToGame(gameId, newClue);

        String exceptionMessage = "There are already as many clues as users! Therefore, this clue can't be added!";
        PostRequestException409 exception = assertThrows(PostRequestException409.class, () -> gameService.addClueToGame(gameId, newClue), exceptionMessage);

        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    @Transactional
    public void updateChosenWord_WordGetsRejected() throws Exception {
        ChosenWordPutDTO chosenWordPutDTO = new ChosenWordPutDTO();
        chosenWordPutDTO.setStatus(false);

        String chosenWord = getFirstWordOnActiveCard();
        gameService.setChosenWord(gameId, chosenWord);
        Game updatedGame = gameService.updateChosenWord(gameId, chosenWordPutDTO);

        assertEquals(ChosenWordStatus.REJECTED, updatedGame.getWordStatus());
    }


    @Test
    @Transactional
    public void updateChosenWord_WordGetsAccepted() throws Exception {
        testGame.addUserId((long)2);
        ChosenWordPutDTO chosenWordPutDTO = new ChosenWordPutDTO();
        chosenWordPutDTO.setStatus(true);

        String chosenWord = getFirstWordOnActiveCard();
        gameService.setChosenWord(gameId, chosenWord);
        Game updatedGame = gameService.updateChosenWord(gameId, chosenWordPutDTO);

        assertEquals(ChosenWordStatus.ACCEPTED, updatedGame.getWordStatus());
    }

    @Test
    @Transactional
    public void updateChosenWord_NotAllUsersResponses() throws Exception {
        ChosenWordPutDTO chosenWordPutDTO = new ChosenWordPutDTO();
        chosenWordPutDTO.setStatus(true);

        Long userId2 = (long)2;
        gameService.addUserToGame(gameId, userId2);

        Long userId3 = (long)3;
        gameService.addUserToGame(gameId, userId3);

        String chosenWord = getFirstWordOnActiveCard();
        gameService.setChosenWord(gameId, chosenWord);
        Game updatedGame = gameService.updateChosenWord(gameId, chosenWordPutDTO);

        assertEquals(ChosenWordStatus.SELECTED, updatedGame.getWordStatus());
    }

    @Test
    public void skipGuessing_ActiveCardGoesToGameBox() throws Exception {
        Card activeCard = gameService.getActiveCard(gameId);
        Game updatedGame = gameService.skipGuessing(gameId);

        assertEquals(activeCard.getId(), updatedGame.getGameBox().getCards().get(0).getId());
    }

    @Test
    @Transactional
    public void finishGame_success_resetsAllAttributes() throws Exception{
        Long userId2 = (long)2;
        Long userId3 = (long)3;
        gameService.addUserToGame(gameId, userId2);
        gameService.addUserToGame(gameId, userId3);

        gameService.startGame(gameId);
        gameService.getActiveCard(gameId);

        ChosenWordPutDTO chosenWordPutDTO = new ChosenWordPutDTO();
        chosenWordPutDTO.setStatus(true);
        String chosenWord = getFirstWordOnActiveCard();
        gameService.setChosenWord(gameId, chosenWord);
        gameService.updateChosenWord(gameId, chosenWordPutDTO);

        Clue clue = new Clue();
        clue.setClueWord("123");
        clue.setTime(5);
        gameService.addClueToGame(gameId, clue);

        Guess guess = new Guess();
        guess.setGuessWord(chosenWord);
        guess.setTime(5);
        gameService.makeGuess(gameId, guess);

        Game finishedGame = gameService.finishGame(gameId);

        assertEquals(GameStatus.FINISHED, finishedGame.getStatus());
        assertNull(finishedGame.getChosenWord());
        assertEquals(ChosenWordStatus.NOCHOSENWORD, finishedGame.getWordStatus());
        assertEquals(0, finishedGame.getWordCounter());
        assertTrue(finishedGame.getChangeWord());
        assertEquals(0, finishedGame.getClues().size());
        assertNull(finishedGame.getGuess());
    }

    @Test
    @Transactional
    public void resetGameStats_success() throws Exception{
        Game updatedGame = gameService.resetGameStats(gameId);

        assertEquals(ChosenWordStatus.NOCHOSENWORD, updatedGame.getWordStatus());
    }

    private String getFirstWordOnActiveCard() throws Exception {
        Card activeCard = gameService.getActiveCard(gameId);
        return activeCard.getMysteryWords().get(0);
    }

}