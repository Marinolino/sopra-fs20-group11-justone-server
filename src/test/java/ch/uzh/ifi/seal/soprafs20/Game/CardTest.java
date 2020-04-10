package ch.uzh.ifi.seal.soprafs20.Game;

import ch.uzh.ifi.seal.soprafs20.entity.Game.Card;
import ch.uzh.ifi.seal.soprafs20.entity.Game.MysteryWord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    List<MysteryWord> wordList = new ArrayList<MysteryWord>();
    Card testCard;

    @BeforeEach
    public void setup(){
        testCard = new Card();
        MysteryWord mysteryWord1 = new MysteryWord();
        MysteryWord mysteryWord2 = new MysteryWord();
        MysteryWord mysteryWord3 = new MysteryWord();
        MysteryWord mysteryWord4 = new MysteryWord();
        MysteryWord mysteryWord5 = new MysteryWord();

        mysteryWord1.setWord("Test1");
        mysteryWord2.setWord("Test2");
        mysteryWord3.setWord("Test3");
        mysteryWord4.setWord("Test4");
        mysteryWord5.setWord("Test5");

        wordList.add(mysteryWord1);
        wordList.add(mysteryWord2);
        wordList.add(mysteryWord3);
        wordList.add(mysteryWord4);
        wordList.add(mysteryWord5);
    }

    @Test
    public void createNewCard(){
        testCard.setWords(wordList);
        assertEquals(testCard.getWords(), wordList);
    }

    @Test
    public void getId(){
        assertNotEquals(testCard.getId(), 10);
        testCard.setId((long) 10);
        assertEquals(testCard.getId(), 10);
    }
}