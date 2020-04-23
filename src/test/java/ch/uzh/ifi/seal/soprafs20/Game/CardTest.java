package ch.uzh.ifi.seal.soprafs20.Game;

import ch.uzh.ifi.seal.soprafs20.entity.Game.Card;
import ch.uzh.ifi.seal.soprafs20.entity.Game.Game;
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
        assertNotEquals(testCard.getId(), 10);
        testCard.setId((long) 10);
        assertEquals(testCard.getId(), 10);
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