package ch.uzh.ifi.seal.soprafs20.rest.mapper;

import ch.uzh.ifi.seal.soprafs20.entity.Game.Card;
import ch.uzh.ifi.seal.soprafs20.entity.Game.Game;
import ch.uzh.ifi.seal.soprafs20.entity.Game.MysteryWord;
import ch.uzh.ifi.seal.soprafs20.entity.User;
import ch.uzh.ifi.seal.soprafs20.rest.dto.*;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

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
    User convertUserPutDTOtoEntity(UserPutDTO userPutDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "score", target = "score")
    @Mapping(source = "gamesPlayed", target = "gamesPlayed")
    @Mapping(source = "inGame", target = "inGame")
    UserGetDTO convertEntityToUserGetDTO(User user);

    @Mapping(source = "userIds", target = "userIds")
    Game convertGamePostDTOtoEntity(GamePostDTO gamePostDTO);

    @Mapping(source = "id", target = "id")
    Game convertGameGetDTOtoEntity(GameGetDTO gameGetDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "token", target = "token")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "normalMode", target = "normalMode")
    @Mapping(source = "score", target = "score")
    @Mapping(source = "round", target = "round")
    @Mapping(source = "userIds", target = "userIds")
    GameGetDTO convertEntityToGameGetDTO(Game game);

    default CardGetDTO convertEntityToCardGetDTO(Card card){
        CardGetDTO cardGetDTO = new CardGetDTO();

        for (MysteryWord mysteryWord : card.getWordList()){
            cardGetDTO.addAWord(mysteryWord.getWord());
            cardGetDTO.addAnId(mysteryWord.getId());
        }
        return cardGetDTO;
    }
}

