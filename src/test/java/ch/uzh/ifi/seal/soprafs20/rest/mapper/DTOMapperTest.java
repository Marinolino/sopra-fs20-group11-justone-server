package ch.uzh.ifi.seal.soprafs20.rest.mapper;

import ch.uzh.ifi.seal.soprafs20.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs20.entity.Game.Card;
import ch.uzh.ifi.seal.soprafs20.entity.Game.MysteryWord;
import ch.uzh.ifi.seal.soprafs20.entity.User;
import ch.uzh.ifi.seal.soprafs20.rest.dto.CardGetDTO;
import ch.uzh.ifi.seal.soprafs20.rest.dto.UserGetDTO;
import ch.uzh.ifi.seal.soprafs20.rest.dto.UserPostDTO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * DTOMapperTest
 * Tests if the mapping between the internal and the external/API representation works.
 */
public class DTOMapperTest {
    @Test
    public void createUser_fromUserPostDTO_toUser_success() {
        // create UserPostDTO
        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setName("username");
        userPostDTO.setUsername("password");

        // MAP -> Create user
        User user = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);

        // check content
        assertEquals(userPostDTO.getUsername(), user.getUsername());
        assertEquals(userPostDTO.getPassword(), user.getPassword());
    }

    @Test
    public void getUser_fromUser_toUserGetDTO_success() {
        // create User
        User user = new User();
        user.setName("Firstname Lastname");
        user.setUsername("firstname@lastname");
        user.setStatus(UserStatus.OFFLINE);
        user.setToken("1");
        user.setScore(10);
        user.setInGame(false);
        user.setGamesPlayed(2);

        // MAP -> Create UserGetDTO
        UserGetDTO userGetDTO = DTOMapper.INSTANCE.convertEntityToUserGetDTO(user);

        // check content
        assertEquals(user.getId(), userGetDTO.getId());
        assertEquals(user.getName(), userGetDTO.getName());
        assertEquals(user.getUsername(), userGetDTO.getUsername());
        assertEquals(user.getStatus(), userGetDTO.getStatus());
        assertEquals(user.getScore(), userGetDTO.getScore());
        assertEquals(user.getInGame(), userGetDTO.getInGame());
        assertEquals(user.getGamesPlayed(), userGetDTO.getGamesPlayed());
    }

    @Test
    public void getCard_fromCard_toCardGetDTO_success(){
        //create card
        List<MysteryWord> wordList = new ArrayList<MysteryWord>();
        Card testCard = new Card();

        MysteryWord mysteryWord1 = new MysteryWord();
        MysteryWord mysteryWord2 = new MysteryWord();
        MysteryWord mysteryWord3 = new MysteryWord();
        MysteryWord mysteryWord4 = new MysteryWord();
        MysteryWord mysteryWord5 = new MysteryWord();

        mysteryWord1.setWord("Test1");
        mysteryWord1.setId((long)1);
        mysteryWord2.setWord("Test2");
        mysteryWord2.setId((long)2);
        mysteryWord3.setWord("Test3");
        mysteryWord3.setId((long)3);
        mysteryWord4.setWord("Test4");
        mysteryWord4.setId((long)4);
        mysteryWord5.setWord("Test5");
        mysteryWord5.setId((long)5);

        wordList.add(mysteryWord1);
        wordList.add(mysteryWord2);
        wordList.add(mysteryWord3);
        wordList.add(mysteryWord4);
        wordList.add(mysteryWord5);

        testCard.setWordList(wordList);

        //create array of words and array of ids
        List<String> words = Arrays.asList(mysteryWord1.getWord(), mysteryWord2.getWord(), mysteryWord3.getWord(), mysteryWord4.getWord(), mysteryWord5.getWord());
        List<Long> ids = Arrays.asList(mysteryWord1.getId(), mysteryWord2.getId(), mysteryWord3.getId(), mysteryWord4.getId(), mysteryWord5.getId());

        // MAP -> Create UserGetDTO
        CardGetDTO cardGetDTO = DTOMapper.INSTANCE.convertEntityToCardGetDTO(testCard);

        assertEquals(cardGetDTO.getWords(), words);
        assertEquals(cardGetDTO.getIds(), ids);
    }
}
