package ch.uzh.ifi.seal.soprafs20.rest.mapper;

import ch.uzh.ifi.seal.soprafs20.constant.ClueStatus;
import ch.uzh.ifi.seal.soprafs20.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs20.entity.game.Card;
import ch.uzh.ifi.seal.soprafs20.entity.game.Clue;
import ch.uzh.ifi.seal.soprafs20.entity.game.Game;
import ch.uzh.ifi.seal.soprafs20.entity.User;
import ch.uzh.ifi.seal.soprafs20.rest.dto.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * DTOMapperTest
 * Tests if the mapping between the internal and the external/API representation works.
 */
public class DTOMapperTest {
    @Test
    public void createUser_fromUserPostDTOToUser_success() {
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
    public void getUser_fromUserToUserGetDTO_success() {
        // create User
        User user = new User();
        user.setName("Firstname Lastname");
        user.setUsername("firstname@lastname");
        user.setStatus(UserStatus.OFFLINE);
        user.setToken("1");
        user.setScore(10);
        user.setGamesPlayed(2);

        // MAP -> Create UserGetDTO
        UserGetDTO userGetDTO = DTOMapper.INSTANCE.convertEntityToUserGetDTO(user);

        // check content
        assertEquals(user.getId(), userGetDTO.getId());
        assertEquals(user.getName(), userGetDTO.getName());
        assertEquals(user.getUsername(), userGetDTO.getUsername());
        assertEquals(user.getStatus(), userGetDTO.getStatus());
        assertEquals(user.getScore(), userGetDTO.getScore());
        assertEquals(user.getGamesPlayed(), userGetDTO.getGamesPlayed());
    }

    @Test
    public void getCard_fromCardToCardGetDTO_success(){
        //create card
        List<String> wordList = new ArrayList<>();
        Card testCard = new Card();

        wordList.add("Test1");
        wordList.add("Test2");
        wordList.add("Test3");
        wordList.add("Test4");
        wordList.add("Test5");

        testCard.setMysteryWords(wordList);

        //create array of words and array of ids
        List<String> words = Arrays.asList("Test1", "Test2", "Test3", "Test4", "Test5");

        // MAP -> Create UserGetDTO
        CardGetDTO cardGetDTO = DTOMapper.INSTANCE.convertEntityToCardGetDTO(testCard);

        assertEquals(cardGetDTO.getWords(), words);
    }

    @Test
    public void getClue_fromGameToClueGetDTO_NotALlUsersHaveGivenClues(){
        Game testGame = new Game();
        Clue testClue1 = new Clue();

        Long userId1 = (long) 1;
        testGame.addUserId(userId1);

        Long userId2 = (long) 2;
        testGame.addUserId(userId2);

        Long userId3 = (long) 3;
        testGame.addUserId(userId3);

        testClue1.setClueWord("B");
        testClue1.setValid(ClueStatus.VALID);
        testGame.addClue(testClue1);

        CluesGetDTO cluesGetDTO = DTOMapper.INSTANCE.convertEntityToCluesGetDTO(testGame);

        assertEquals("B", cluesGetDTO.getClues().get(0));
        assertFalse(cluesGetDTO.getAllAutomaticClues());
    }

    @Test
    public void getClue_fromGameToClueGetDTO_ALlUsersHaveGivenClues(){
        Game testGame = new Game();
        Clue testClue = new Clue();
        Long userId1 = (long) 1;
        Long userId2 = (long) 2;

        testGame.addUserId(userId1);
        testGame.addUserId(userId2);
        testClue.setClueWord("B");
        testClue.setValid(ClueStatus.VALID);
        testGame.addClue(testClue);


        CluesGetDTO cluesGetDTO = DTOMapper.INSTANCE.convertEntityToCluesGetDTO(testGame);

        assertEquals("B", cluesGetDTO.getClues().get(0));
        assertTrue(cluesGetDTO.getAllAutomaticClues());
    }
}
