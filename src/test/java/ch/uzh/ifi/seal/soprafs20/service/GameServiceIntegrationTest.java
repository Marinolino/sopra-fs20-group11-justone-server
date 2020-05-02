package ch.uzh.ifi.seal.soprafs20.service;

import ch.uzh.ifi.seal.soprafs20.constant.ChosenWordStatus;
import ch.uzh.ifi.seal.soprafs20.constant.ClueStatus;
import ch.uzh.ifi.seal.soprafs20.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs20.entity.Game.Card;
import ch.uzh.ifi.seal.soprafs20.entity.Game.Clue;
import ch.uzh.ifi.seal.soprafs20.entity.Game.Game;
import ch.uzh.ifi.seal.soprafs20.exceptions.API.POST.PostRequestException409;
import ch.uzh.ifi.seal.soprafs20.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs20.rest.dto.ChosenWordPutDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
    }

    @Test
    public void getAllGames_noGamesInRepo(){
        gameRepository.deleteAll();
        List<Game> games = gameService.getGames();

        assertEquals(games.size(), 0);
    }

    @Test
    public void createGame_success() throws Exception {
        assertEquals(createdGame.getScore(), 0);
        assertEquals(createdGame.getRound(), 0);
        assertEquals(createdGame.getStatus(), GameStatus.CREATED);
        assertEquals(createdGame.getUserIds().size(), 1);
        assertNull(createdGame.getChosenWord());
    }

    @Test
    public void startGame_moreThanThreeUsers() throws Exception {
        Long userId2 = (long)2;
        Long userId3 = (long)3;
        Long userId4 = (long)4;

        Long gameId = createdGame.getId();

        Game updatedGame = gameService.addUserToGame(gameId, userId2);
        updatedGame = gameService.addUserToGame(gameId, userId3);
        updatedGame = gameService.addUserToGame(gameId, userId4);

        updatedGame = gameService.startGame(gameId);

        assertEquals(updatedGame.getStatus(), GameStatus.RUNNING);
        assertTrue(updatedGame.getNormalMode());
    }

    @Test
    public void startGame_threeUsers() throws Exception {
        Long userId2 = (long)2;
        Long userId3 = (long)3;

        Long gameId = createdGame.getId();

        Game updatedGame = gameService.addUserToGame(gameId, userId2);

        updatedGame = gameService.addUserToGame(gameId, userId3);

        updatedGame = gameService.startGame(gameId);

        assertEquals(updatedGame.getStatus(), GameStatus.RUNNING);
        assertFalse(updatedGame.getNormalMode());
    }

    @Test
    public void addUserToGame_success() throws Exception {
        Long userId1 = (long)(1);
        Long userId2 = (long)(2);
        Game updatedGame = gameService.addUserToGame(createdGame.getId(), userId2);

        assertEquals(updatedGame.getUserIds().size(), 2);
        assertEquals(updatedGame.getUserIds().get(0), userId1);
        assertEquals(updatedGame.getUserIds().get(1), userId2);
    }

    @Test
    public void removeUserFromGame_success() throws Exception {
        Long userId1 = (long)(1);
        Long userId2 = (long)(2);
        Game updatedGame = gameService.addUserToGame(createdGame.getId(), userId2);

        updatedGame = gameService.removeUserFromGame(updatedGame.getId(), userId1);

        assertEquals(updatedGame.getUserIds().size(), 1);
        assertEquals(updatedGame.getUserIds().get(0), userId2);
    }



    @Test
    public void findGameById_success() throws Exception {
        Game gameById = gameService.getGameById(createdGame.getId());

        assertEquals(createdGame.getId(), gameById.getId());
    }

    @Test
    @Transactional
    public void setChosenWord() throws Exception {
        Long gameId = createdGame.getId();
        Card activeCard = gameService.getActiveCard(gameId);
        String chosenWord = activeCard.getMysteryWords().get(0);
        Game updatedGame = gameService.setChosenWord(gameId, chosenWord);

        assertEquals(updatedGame.getChosenWord(), chosenWord);
        assertEquals(updatedGame.getWordStatus(), ChosenWordStatus.SELECTED);
    }

    @Test
    public void getActiveCard_success() throws Exception {
        gameService.getActiveCard(createdGame.getId());
    }

    @Test
    @Transactional
    public void addClueToGame_success() throws Exception {
        Clue newClue = new Clue();
        String clue = "TestClue";

        newClue.setClue(clue);
        Long gameId = createdGame.getId();
        Card activeCard = gameService.getActiveCard(gameId);
        String chosenWord = activeCard.getMysteryWords().get(0);
        gameService.setChosenWord(gameId, chosenWord);
        Clue testClue = gameService.addClueToGame(gameId, newClue);

        assertEquals(testClue.getClue(), clue);
        assertEquals(testClue.getValid(), ClueStatus.VALID);
    }

    @Test
    @Transactional
    public void addClueToGame_amountOfUsersIsEqualToClues() throws Exception {
        Clue newClue = new Clue();
        String clue = "TestClue";

        newClue.setClue(clue);
        Long gameId = createdGame.getId();
        Card activeCard = gameService.getActiveCard(gameId);
        String chosenWord = activeCard.getMysteryWords().get(0);
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

        Long gameId = createdGame.getId();
        Card activeCard = gameService.getActiveCard(gameId);
        String chosenWord = activeCard.getMysteryWords().get(0);
        gameService.setChosenWord(gameId, chosenWord);
        Game updatedGame = gameService.updateChosenWord(gameId, chosenWordPutDTO);

        assertEquals(ChosenWordStatus.REJECTED, updatedGame.getWordStatus());
    }

    @Test
    @Transactional
    public void updateChosenWord_WordGetsAccepted() throws Exception {
        ChosenWordPutDTO chosenWordPutDTO = new ChosenWordPutDTO();
        chosenWordPutDTO.setStatus(true);

        Long gameId = createdGame.getId();
        Card activeCard = gameService.getActiveCard(gameId);
        String chosenWord = activeCard.getMysteryWords().get(0);
        gameService.setChosenWord(gameId, chosenWord);
        Game updatedGame = gameService.updateChosenWord(gameId, chosenWordPutDTO);

        assertEquals(ChosenWordStatus.ACCEPTED, updatedGame.getWordStatus());
    }

    @Test
    @Transactional
    public void updateChosenWord_NotAllUsersResponses() throws Exception {
        ChosenWordPutDTO chosenWordPutDTO = new ChosenWordPutDTO();
        chosenWordPutDTO.setStatus(true);

        Long gameId = createdGame.getId();
        Long userId = (long)2;
        gameService.addUserToGame(gameId, userId);
        Card activeCard = gameService.getActiveCard(gameId);
        String chosenWord = activeCard.getMysteryWords().get(0);
        gameService.setChosenWord(gameId, chosenWord);
        Game updatedGame = gameService.updateChosenWord(gameId, chosenWordPutDTO);

        assertEquals(ChosenWordStatus.SELECTED, updatedGame.getWordStatus());
    }




}