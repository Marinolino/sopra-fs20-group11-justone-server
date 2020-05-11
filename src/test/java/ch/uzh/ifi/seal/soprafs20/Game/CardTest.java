package ch.uzh.ifi.seal.soprafs20.Game;

import ch.uzh.ifi.seal.soprafs20.entity.game.Card;
import ch.uzh.ifi.seal.soprafs20.entity.game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    List<String> wordList = new ArrayList<>();
    Card testCard;

    @BeforeEach
    public void setup(){
        testCard = new Card();
        wordList.add("Test1");
        wordList.add("Test2");
        wordList.add("Test3");
        wordList.add("Test4");
        wordList.add("Test5");
    }

    @Test
    public void createNewCard(){
        testCard.setMysteryWords(wordList);
        assertEquals(testCard.getMysteryWords(), wordList);
    }

    @Test
    public void getId(){
        assertNotEquals(10, testCard.getId());
        testCard.setId((long) 10);
        assertEquals(10, testCard.getId());
    }

    @Test
    public void setChosenWord(){
        Game testGame = new Game();
        String chosenWord = "Test4";

        testCard.setMysteryWords(wordList);
        testGame.setActiveCard(testCard);
        testGame.setChosenWord("Test4");

        assertEquals(testGame.getChosenWord(), wordList.get(3));
    }
}