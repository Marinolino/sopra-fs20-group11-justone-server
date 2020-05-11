package ch.uzh.ifi.seal.soprafs20.controller;

import ch.uzh.ifi.seal.soprafs20.entity.game.Card;
import ch.uzh.ifi.seal.soprafs20.entity.game.Clue;
import ch.uzh.ifi.seal.soprafs20.entity.game.Game;
import ch.uzh.ifi.seal.soprafs20.entity.game.Guess;
import ch.uzh.ifi.seal.soprafs20.exceptions.api.get.GetRequestException404;
import ch.uzh.ifi.seal.soprafs20.rest.dto.*;
import ch.uzh.ifi.seal.soprafs20.rest.mapper.DTOMapper;
import ch.uzh.ifi.seal.soprafs20.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public GameGetDTO createGame(@RequestBody GamePostDTO gamePostDTO) throws Exception{
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
        Game updatedGame = gameService.addUserToGame(id, gameInput.getCurrentUserId());

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
        Game updatedGame = gameService.removeUserFromGame(id, gameInput.getCurrentUserId());

        // convert internal representation of game back to API
        return DTOMapper.INSTANCE.convertEntityToGameGetDTO(updatedGame);
    }

    @PutMapping("/games/start/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public GameGetDTO startGame(@PathVariable("id") long id) throws Exception {
        // start game
        Game startedGame = gameService.startGame(id);

        // convert internal representation of game back to API
        return DTOMapper.INSTANCE.convertEntityToGameGetDTO(startedGame);
    }

    @PutMapping("/games/finish/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public GameGetDTO finishGame(@PathVariable("id") long id) throws Exception {
        Game updatedGame = gameService.finishGame(id);

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
    public GameGetDTO getGameById(@PathVariable("id") long id) {

        //look for game in database
        Game gameById = gameService.getGameById(id);

        //check if a game was found
        if (gameById == null){
            throw new GetRequestException404("No game was found!");
        }

        // convert internal representation of game to API
        return DTOMapper.INSTANCE.convertEntityToGameGetDTO(gameById);
    }

    @PutMapping("/games/reset/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public GameGetDTO resetGameStats(@PathVariable("id") long id) {
        Game updatedGame = gameService.resetGameStats(id);

        return DTOMapper.INSTANCE.convertEntityToGameGetDTO(updatedGame);
    }

    @GetMapping("/cards/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CardGetDTO getActiveCard(@PathVariable("id") long id) throws Exception {
        Game gameById = gameService.getGameById(id);

        Card activeCard = gameById.getActiveCard();

        //convert internal representation of card to API
        return DTOMapper.INSTANCE.convertEntityToCardGetDTO(activeCard);
    }

    @PutMapping("/cards/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CardGetDTO nextTurn(@PathVariable("id") long id) throws Exception {

        Card activeCard = gameService.getActiveCard(id);

        //convert internal representation of card to API
        return DTOMapper.INSTANCE.convertEntityToCardGetDTO(activeCard);
    }

    @GetMapping("/chosenword/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ChosenWordGetDTO getChosenWord(@PathVariable("id") long id) {
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

    @PostMapping("/clues/{id1}/{id2}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ArrayList<ClueGetDTO> createClue(@PathVariable("id1") long id1, @PathVariable("id2") long id2,
                                            @RequestBody ArrayList<CluePostDTO> cluePostDTOList) throws Exception {
        // convert API clue to internal representation
        Clue clueInput1 = DTOMapper.INSTANCE.convertCluePostDTOtoEntity(cluePostDTOList.get(0));
        Clue clueInput2 = DTOMapper.INSTANCE.convertCluePostDTOtoEntity(cluePostDTOList.get(1));

        // create clue
        Clue checkedClue1 = gameService.addClueToGame(id1, clueInput1);
        Clue checkedClue2 = gameService.addClueToGame(id2, clueInput2);

        ArrayList<ClueGetDTO> checkedClueList = new ArrayList<>();
        // convert internal representation of clue back to API
        checkedClueList.add(DTOMapper.INSTANCE.convertEntityToClueGetDTO(checkedClue1));
        checkedClueList.add(DTOMapper.INSTANCE.convertEntityToClueGetDTO(checkedClue2));

        return checkedClueList;
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
    public CluesGetDTO setCluesToInvalid(@PathVariable("id") long id, @RequestBody CluePutDTO cluePutDTO) {
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
    public GameGetDTO skipGuessing(@PathVariable("id") long id) throws Exception {
        Game updatedGame = gameService.skipGuessing(id);

        // convert internal representation of clue back to API
        return DTOMapper.INSTANCE.convertEntityToGameGetDTO(updatedGame);
   }

   @GetMapping("/guess/{id}")
   @ResponseStatus(HttpStatus.OK)
   @ResponseBody
   public GuessGetDTO getGuess(@PathVariable("id") long id) {
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