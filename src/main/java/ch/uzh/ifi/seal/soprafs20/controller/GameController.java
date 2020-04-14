package ch.uzh.ifi.seal.soprafs20.controller;

import ch.uzh.ifi.seal.soprafs20.entity.Game.Card;
import ch.uzh.ifi.seal.soprafs20.entity.Game.Game;
import ch.uzh.ifi.seal.soprafs20.exceptions.API.GET.GetRequestException404;
import ch.uzh.ifi.seal.soprafs20.exceptions.API.GET.GetRequestException409;
import ch.uzh.ifi.seal.soprafs20.exceptions.API.GET.GetRequestException500;
import ch.uzh.ifi.seal.soprafs20.rest.dto.CardGetDTO;
import ch.uzh.ifi.seal.soprafs20.rest.dto.CardPutDTO;
import ch.uzh.ifi.seal.soprafs20.rest.dto.GameGetDTO;
import ch.uzh.ifi.seal.soprafs20.rest.dto.GamePostDTO;
import ch.uzh.ifi.seal.soprafs20.rest.mapper.DTOMapper;
import ch.uzh.ifi.seal.soprafs20.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
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
    public GameGetDTO createUser(@RequestBody GamePostDTO gamePostDTO) throws FileNotFoundException {
        // convert API user to internal representation
        Game gameInput = DTOMapper.INSTANCE.convertGamePostDTOtoEntity(gamePostDTO);

        // create game
        Game createdGame = gameService.createGame(gameInput);

        // convert internal representation of game back to API
        return DTOMapper.INSTANCE.convertEntityToGameGetDTO(createdGame);
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
    public CardGetDTO getActiveCard(@PathVariable("id") long id) throws GetRequestException500 {
        Card activeCard;

        //get topCard from deck
        try {
            activeCard = gameService.getActiveCard(id);
        }
        catch (Exception e){
            throw new GetRequestException500("Something went wrong when fetching the card!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // convert internal representation of card to API
        return DTOMapper.INSTANCE.convertEntityToCardGetDTO(activeCard);
    }

    @PutMapping("/cards/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public GameGetDTO setChosenWord(@PathVariable("id") long id, @RequestBody CardPutDTO cardPutDTO) throws GetRequestException404 {

        try {
            gameService.setChosenWord(id, cardPutDTO);
        }
        catch (Exception e){
            throw new GetRequestException404("Something went wrong!", HttpStatus.NOT_FOUND);
        }

        // convert internal representation of game to API
        return DTOMapper.INSTANCE.convertEntityToGameGetDTO(gameService.getGameById(id));
    }



    //TODO put requests
    /*@PutMapping(value = "/games/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public GameGetDTO updateGame(@RequestBody GamePutDTO gamePutDTO) {
    }
    */

}