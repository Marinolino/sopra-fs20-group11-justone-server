package ch.uzh.ifi.seal.soprafs20.rest.mapper;

import ch.uzh.ifi.seal.soprafs20.constant.ClueStatus;
import ch.uzh.ifi.seal.soprafs20.entity.game.Card;
import ch.uzh.ifi.seal.soprafs20.entity.game.Clue;
import ch.uzh.ifi.seal.soprafs20.entity.game.Game;
import ch.uzh.ifi.seal.soprafs20.entity.game.Guess;
import ch.uzh.ifi.seal.soprafs20.entity.User;
import ch.uzh.ifi.seal.soprafs20.rest.dto.*;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

/**
 * DTOMapper
 * This class is responsible for generating classes that will automatically transform/map the internal representation
 * of an entity (e.g., the User) to the external/API representation (e.g., UserGetDTO for getting, UserPostDTO for creating)
 * and vice versa.
 * Additional mappers can be defined for new entities.
 * Always created one mapper for getting information (GET) and one mapper for creating information (POST).
 */
@Mapper
public interface DTOMapper {

    DTOMapper INSTANCE = Mappers.getMapper(DTOMapper.class);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    User convertUserPostDTOtoEntity(UserPostDTO userPostDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "correctlyGuessed", target = "correctlyGuessed")
    @Mapping(source = "duplicateClues", target = "duplicateClues")
    User convertUserPutDTOtoEntity(UserPutDTO userPutDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "correctlyGuessed", target = "correctlyGuessed")
    @Mapping(source = "duplicateClues", target = "duplicateClues")
    @Mapping(source = "score", target = "score")
    @Mapping(source = "gamesPlayed", target = "gamesPlayed")
    UserGetDTO convertEntityToUserGetDTO(User user);

    @Mapping(source = "currentUserId", target = "currentUserId")
    Game convertGamePostDTOtoEntity(GamePostDTO gamePostDTO);

    @Mapping(source = "currentUserId", target = "currentUserId")
    Game convertGamePutDTOtoEntity(GamePutDTO gamePutDTO);

    @Mapping(source = "clueWord", target = "clueWord")
    @Mapping(source = "time", target = "time")
    Clue convertCluePostDTOtoEntity(CluePostDTO cluePostDTO);

    @Mapping(source = "guessWord", target = "guessWord")
    @Mapping(source = "time", target = "time")
    Guess convertGuessPostDTOtoEntity(GuessPostDTO guessPostDTO);

    default List<String> convertCluePutDTOtoList(CluePutDTO clueDeleteDTO){
        List<String> cluesToChange;
        cluesToChange  = clueDeleteDTO.getCluesToChange();
        return cluesToChange ;
    }

    @Mapping(source = "id", target = "id")
    @Mapping(source = "token", target = "token")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "normalMode", target = "normalMode")
    @Mapping(source = "changeWord", target = "changeWord")
    @Mapping(source = "score", target = "score")
    @Mapping(source = "round", target = "round")
    @Mapping(source = "deckSize", target = "deckSize")
    @Mapping(source = "chosenWord", target = "chosenWord")
    @Mapping(source = "wordStatus", target = "wordStatus")
    @Mapping(source = "currentUserId", target = "currentUserId")
    @Mapping(source = "userIds", target = "userIds")
    GameGetDTO convertEntityToGameGetDTO(Game game);

    @Mapping(source = "clueWord", target = "clueWord")
    @Mapping(source = "time", target = "time")
    @Mapping(source = "valid", target = "valid")
    ClueGetDTO convertEntityToClueGetDTO(Clue clue);

    @Mapping(source = "guessWord", target = "guessWord")
    @Mapping(source = "time", target = "time")
    @Mapping(source = "guessStatus", target = "guessStatus")
    GuessGetDTO convertEntityToGuessGetDTO(Guess guess);

    @Mapping(source = "chosenWord", target = "chosenWord")
    @Mapping(source = "wordStatus", target = "wordStatus")
    ChosenWordGetDTO convertEntityToChosenWordGetDTO(Game game);

    default CluesGetDTO convertEntityToCluesGetDTO(Game game) {
        CluesGetDTO cluesGetDTO = new CluesGetDTO();

        //add all valid clues to the list
        for (Clue clue : game.getClues()) {
            if (clue.getValid() == ClueStatus.VALID) {
                cluesGetDTO.addAClue(clue.getClueWord());
            }
        }
        //check if all users have given clues
        cluesGetDTO.setAllAutomaticClues(game.getUserIds().size() - 1 == game.getClues().size());
        cluesGetDTO.setAllManualClues(game.getUserIds().size() - 1 == game.getClueCounter());
        return cluesGetDTO;
    }


    default CardGetDTO convertEntityToCardGetDTO(Card card){
        CardGetDTO cardGetDTO = new CardGetDTO();
        for (String mysteryWord : card.getMysteryWords()){
            cardGetDTO.addAWord(mysteryWord);
        }
        return cardGetDTO;
    }
}

