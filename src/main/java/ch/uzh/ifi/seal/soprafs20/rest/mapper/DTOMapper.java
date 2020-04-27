package ch.uzh.ifi.seal.soprafs20.rest.mapper;

import ch.uzh.ifi.seal.soprafs20.constant.ClueStatus;
import ch.uzh.ifi.seal.soprafs20.entity.Game.Card;
import ch.uzh.ifi.seal.soprafs20.entity.Game.Clue;
import ch.uzh.ifi.seal.soprafs20.entity.Game.Game;
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

    @Mapping(source = "clue", target = "clue")
    @Mapping(source = "time", target = "time")
    Clue convertCluePostDTOtoEntity(CluePostDTO cluePostDTO);

    default List<String> convertCluePutDTOtoList(CluePutDTO clueDeleteDTO){
        List<String> cluesToChange = new ArrayList<>();
        cluesToChange  = clueDeleteDTO.getCluesToChange();
        return cluesToChange ;
    }

    @Mapping(source = "id", target = "id")
    @Mapping(source = "token", target = "token")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "normalMode", target = "normalMode")
    @Mapping(source = "score", target = "score")
    @Mapping(source = "round", target = "round")
    @Mapping(source = "chosenWord", target = "chosenWord")
    @Mapping(source = "userIds", target = "userIds")
    GameGetDTO convertEntityToGameGetDTO(Game game);

    @Mapping(source = "clue", target = "clue")
    @Mapping(source = "time", target = "time")
    @Mapping(source = "valid", target = "valid")
    ClueGetDTO convertEntityToClueGetDTO(Clue clue);

    default CluesGetDTO convertEntityToCluesGetDTO(Game game) {
        CluesGetDTO cluesGetDTO = new CluesGetDTO();

        //add all valid clues to the list
        for (Clue clue : game.getClues()) {
            if (clue.getValid() == ClueStatus.VALID) {
                cluesGetDTO.addAClue(clue.getClue());
            }
            if (game.getUserIds().size() == game.getClues().size()) {
                cluesGetDTO.setAllClues(true);
            }
            else {
                cluesGetDTO.setAllClues(false);
            }
        }
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

