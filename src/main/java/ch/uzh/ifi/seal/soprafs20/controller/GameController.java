package ch.uzh.ifi.seal.soprafs20.controller;

import ch.uzh.ifi.seal.soprafs20.entity.Game.Game;
import ch.uzh.ifi.seal.soprafs20.exceptions.API.GET.GetRequestException404;
import ch.uzh.ifi.seal.soprafs20.rest.dto.GameGetDTO;
import ch.uzh.ifi.seal.soprafs20.rest.dto.GamePostDTO;
import ch.uzh.ifi.seal.soprafs20.rest.mapper.DTOMapper;
import ch.uzh.ifi.seal.soprafs20.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

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

    //TODO put requests
    /*@PutMapping(value = "/games/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public GameGetDTO updateGame(@RequestBody GamePutDTO gamePutDTO) {
    }
    */

}
