package ch.uzh.ifi.seal.soprafs20.controller;

import ch.uzh.ifi.seal.soprafs20.entity.Game.Card;
import ch.uzh.ifi.seal.soprafs20.entity.Game.Clue;
import ch.uzh.ifi.seal.soprafs20.entity.Game.Game;
import ch.uzh.ifi.seal.soprafs20.entity.Game.Guess;
import ch.uzh.ifi.seal.soprafs20.exceptions.API.GET.GetRequestException404;
import ch.uzh.ifi.seal.soprafs20.rest.dto.*;
import ch.uzh.ifi.seal.soprafs20.rest.mapper.DTOMapper;
import ch.uzh.ifi.seal.soprafs20.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class GameController {

    private final GameService gameService;

    GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/games")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public GameGetDTO createGame(@RequestBody GamePostDTO gamePostDTO) throws FileNotFoundException {
        // convert API game to internal representation
        Game gameInput = DTOMapper.INSTANCE.convertGamePostDTOtoEntity(gamePostDTO);

        // create game
        Game createdGame = gameService.createGame(gameInput);

        // convert internal representation of game back to API
        return DTOMapper.INSTANCE.convertEntityToGameGetDTO(createdGame);
    }

    @PutMapping("/games/join/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public GameGetDTO addUserToGame(@PathVariable("id") long id, @RequestBody GamePutDTO gamePutDTO) throws Exception {
        // convert API game to internal representation
        Game gameInput = DTOMapper.INSTANCE.convertGamePutDTOtoEntity(gamePutDTO);

        // create game
        Game updatedGame = gameService.addUserToGame(id, gameInput);

        // convert internal representation of game back to API
        return DTOMapper.INSTANCE.convertEntityToGameGetDTO(updatedGame);
    }

    @PutMapping("/games/leave/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public GameGetDTO removeUserFromGame(@PathVariable("id") long id, @RequestBody GamePutDTO gamePutDTO) throws Exception {
        // convert API game to internal representation
        Game gameInput = DTOMapper.INSTANCE.convertGamePutDTOtoEntity(gamePutDTO);

        // create game
        Game updatedGame = gameService.removeUserFromGame(id, gameInput);

        // convert internal representation of game back to API
        return DTOMapper.INSTANCE.convertEntityToGameGetDTO(updatedGame);
    }

    @PutMapping("/games/start/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public GameGetDTO startGame(@PathVariable("id") long id)  throws FileNotFoundException {
        // start game
        Game startedGame = gameService.startGame(id);

        // convert internal representation of game back to API
        return DTOMapper.INSTANCE.convertEntityToGameGetDTO(startedGame);
    }

    @PutMapping("/games/finish/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public GameGetDTO finishGame(@PathVariable("id") long id) {
        Game updatedGame = gameService.finishGame(id);

        //convert internal representation of game to API
        return DTOMapper.INSTANCE.convertEntityToGameGetDTO(updatedGame);
    }

    @GetMapping("/games")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<GameGetDTO> getAllGames() {
        List<GameGetDTO> gameGetDTOs = new ArrayList<>();
        List<Game> games = gameService.getGames();

        for (Game game : games) {
            gameGetDTOs.add(DTOMapper.INSTANCE.convertEntityToGameGetDTO(game));
        }
        return gameGetDTOs;
    }

    @GetMapping("/games/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public GameGetDTO getGameById(@PathVariable("id") long id) throws GetRequestException404 {

        //look for game in database
        Game gameById = gameService.getGameById(id);

        //check if a game was found
        if (gameById == null){
            throw new GetRequestException404("No game was found!", HttpStatus.NOT_FOUND);
        }

        // convert internal representation of game to API
        return DTOMapper.INSTANCE.convertEntityToGameGetDTO(gameById);
    }

    @GetMapping("/cards/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CardGetDTO getActiveCard(@PathVariable("id") long id) throws Exception {

        Card activeCard = gameService.getActiveCard(id);

        //convert internal representation of card to API
        return DTOMapper.INSTANCE.convertEntityToCardGetDTO(activeCard);
    }

    @GetMapping("/chosenword/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ChosenWordGetDTO getChosenWord(@PathVariable("id") long id) throws Exception {
        //set the chosen word for the specified game
        Game updatedGame = gameService.getGameById(id);

        //convert internal representation of game to API
        return DTOMapper.INSTANCE.convertEntityToChosenWordGetDTO(updatedGame);
    }

    @PutMapping("/chosenword/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ChosenWordGetDTO setChosenWord(@PathVariable("id") long id, @RequestBody CardPutDTO cardPutDTO) throws Exception {

        String chosenWord = cardPutDTO.getChosenWord();

        //set the chosen word for the specified game
        Game updatedGame = gameService.setChosenWord(id, chosenWord);

        // convert internal representation of game to API
        return DTOMapper.INSTANCE.convertEntityToChosenWordGetDTO(updatedGame);
    }

    @PutMapping("/chosenword/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ChosenWordGetDTO updateChosenWord(@PathVariable("id") long id, @RequestBody ChosenWordPutDTO chosenWordPutDTO) throws Exception {
        //set the chosen word for the specified game
        Game updatedGame = gameService.updateChosenWord(id, chosenWordPutDTO);

        // convert internal representation of game to API
        return DTOMapper.INSTANCE.convertEntityToChosenWordGetDTO(updatedGame);
    }

    @PostMapping("/clues/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ClueGetDTO createClue(@PathVariable("id") long id, @RequestBody CluePostDTO cluePostDTO) throws Exception {
        // convert API clue to internal representation
        Clue clueInput = DTOMapper.INSTANCE.convertCluePostDTOtoEntity(cluePostDTO);

        // create clue
        Clue checkedClue = gameService.addClueToGame(id, clueInput);

        // convert internal representation of clue back to API
        return DTOMapper.INSTANCE.convertEntityToClueGetDTO(checkedClue);
    }

    @GetMapping("/clues/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CluesGetDTO getAllClues(@PathVariable("id") long id) throws GetRequestException404 {
        Game gameById = gameService.getGameById(id);

        // convert internal representation of clue back to API
        return DTOMapper.INSTANCE.convertEntityToCluesGetDTO(gameById);
    }

    @PutMapping("/clues/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CluesGetDTO setCluesToInvalid(@PathVariable("id") long id, @RequestBody CluePutDTO cluePutDTO) throws GetRequestException404 {
        // convert API clue to internal representation
        List<String> cluesToChange = DTOMapper.INSTANCE.convertCluePutDTOtoList(cluePutDTO);

        // create clue
        Game updatedGame = gameService.setCluesToInvalid(id, cluesToChange);

        // convert internal representation of clue back to API
        return DTOMapper.INSTANCE.convertEntityToCluesGetDTO(updatedGame);
    }

    @PutMapping("/skip/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public GameGetDTO skipGuessing(@PathVariable("id") long id) throws GetRequestException404 {
        Game updatedGame = gameService.skipGuessing(id);

        // convert internal representation of clue back to API
        return DTOMapper.INSTANCE.convertEntityToGameGetDTO(updatedGame);
   }

   @GetMapping("/guess/{id}")
   @ResponseStatus(HttpStatus.OK)
   @ResponseBody
   public GuessGetDTO getGuess(@PathVariable("id") long id) throws Exception{
        Guess guess = gameService.getGuess(id);

        return DTOMapper.INSTANCE.convertEntityToGuessGetDTO(guess);
    }

    @PostMapping("/guess/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public GuessGetDTO setGuess(@PathVariable("id") long id,@RequestBody GuessPostDTO guessPostDTO) throws Exception {
        Guess guessInput = DTOMapper.INSTANCE.convertGuessPostDTOtoEntity(guessPostDTO);

        Guess guess = gameService.makeGuess(id, guessInput);

        return DTOMapper.INSTANCE.convertEntityToGuessGetDTO(guess);
    }
}