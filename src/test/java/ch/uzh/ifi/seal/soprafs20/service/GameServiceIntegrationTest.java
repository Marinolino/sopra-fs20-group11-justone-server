package ch.uzh.ifi.seal.soprafs20.service;

import ch.uzh.ifi.seal.soprafs20.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs20.entity.Game.Game;
import ch.uzh.ifi.seal.soprafs20.entity.Game.MysteryWord;
import ch.uzh.ifi.seal.soprafs20.exceptions.API.GET.GetRequestException404;
import ch.uzh.ifi.seal.soprafs20.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs20.rest.dto.CardPutDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@WebAppConfiguration
@SpringBootTest
class GameServiceIntegrationTest {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameService gameService;


    @BeforeEach
    public void setup() {
        gameRepository.deleteAll();
    }

    @Test
    public void getAllGames_noGamesInRepo(){
        List<Game> games = gameService.getGames();

        assertEquals(games.size(), 0);
    }

    @Test
    public void createGame_success() throws Exception {
        Game testGame = new Game();
        testGame.setCurrentUserId((long)1);

        Game createdGame = gameService.createGame(testGame);

        assertEquals(createdGame.getScore(), 0);
        assertEquals(createdGame.getRound(), 0);
        assertEquals(createdGame.getStatus(), GameStatus.CREATED);
        assertEquals(createdGame.getUserIds().size(), 1);
    }

    @Test
    public void addUserToGame_success() throws Exception {
        Game testGame = new Game();
        testGame.setCurrentUserId((long)1);

        Game createdGame = gameService.createGame(testGame);
        createdGame.addUserId((long)2);

        assertEquals(createdGame.getUserIds().size(), 2);
    }

    @Test
    public void findGameById_success() throws Exception {
        Game testGame = new Game();
        testGame.setCurrentUserId((long)1);

        Game createdGame = gameService.createGame(testGame);
        Game gameById = gameService.getGameById(createdGame.getId());

        assertEquals(createdGame.getId(), gameById.getId());
    }

    @Test
    public void getActiveCard_success() throws Exception {
        Game newGame = new Game();
        newGame.setCurrentUserId((long)1);

        Game createdGame = gameService.createGame(newGame);
        gameService.getActiveCard(createdGame.getId());
    }

    @Test
    public void setChosenWord_success() throws Exception {
        Game newGame = new Game();
        newGame.setCurrentUserId((long)1);

        Game createdGame = gameService.createGame(newGame);
        CardPutDTO cardPutDTO = new CardPutDTO();
        MysteryWord chosenWord = createdGame.getActiveCard().getWordList().get(3);
        cardPutDTO.setId(chosenWord.getId());

        gameService.setChosenWord(createdGame.getId(), cardPutDTO);

        Game existedGame = gameService.getGameById(createdGame.getId());

        assertEquals(chosenWord.getId(), existedGame.getActiveCard().getWordList().get(3).getId());
        assertEquals(true, existedGame.getActiveCard().getWordList().get(3).getChosen());
    }

    @Test
    public void setChosenWord_faliure() {

        String exceptionMessage = "No game was found!";
        GetRequestException404 exception = assertThrows(GetRequestException404.class, () -> gameService.setChosenWord(1l, new CardPutDTO()), exceptionMessage);
        assertEquals(exceptionMessage, exception.getMessage());

    }
}