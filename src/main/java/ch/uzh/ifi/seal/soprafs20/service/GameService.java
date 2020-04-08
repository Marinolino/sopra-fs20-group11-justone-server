package ch.uzh.ifi.seal.soprafs20.service;

import ch.uzh.ifi.seal.soprafs20.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs20.entity.Game;
import ch.uzh.ifi.seal.soprafs20.exceptions.API.GET.GetRequestException;
import ch.uzh.ifi.seal.soprafs20.repository.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class GameService {

    private final Logger log = LoggerFactory.getLogger(GameService.class);

    private final GameRepository gameRepository;

    @Autowired
    public GameService(@Qualifier("gameRepository") GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> getGames() {
        return this.gameRepository.findAll();
    }

    public Game getGameById(Long Id){
        Game gameById = this.gameRepository.findById(Id).orElse(null);

        if (gameById == null){
            String message = "No game was found!";
            throw new GetRequestException(message, HttpStatus.NOT_FOUND);
        }
        return gameById;
    }

    public Game createGame(Game newGame) {

        newGame.setToken(UUID.randomUUID().toString());
        newGame.setStatus(GameStatus.RUNNING);

        // saves the given entity but data is only persisted in the database once flush() is called
        newGame = gameRepository.save(newGame);
        gameRepository.flush();

        log.debug("Created Information for Game: {}", newGame);
        return newGame;
    }


}
