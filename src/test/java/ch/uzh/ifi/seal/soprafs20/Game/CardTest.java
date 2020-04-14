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
        mysteryWord1.setId((long)1);
        mysteryWord1.setChosen(false);

        mysteryWord2.setWord("Test2");
        mysteryWord2.setId((long)2);
        mysteryWord2.setChosen(false);

        mysteryWord3.setWord("Test3");
        mysteryWord3.setId((long)3);
        mysteryWord3.setChosen(false);

        mysteryWord4.setWord("Test4");
        mysteryWord4.setId((long)4);
        mysteryWord4.setChosen(false);

        mysteryWord5.setWord("Test5");
        mysteryWord5.setId((long)5);
        mysteryWord5.setChosen(false);

        wordList.add(mysteryWord1);
        wordList.add(mysteryWord2);
        wordList.add(mysteryWord3);
        wordList.add(mysteryWord4);
        wordList.add(mysteryWord5);
    }

    @Test
    public void createNewCard(){
        testCard.setWordList(wordList);
        assertEquals(testCard.getWordList(), wordList);
    }

    @Test
    public void getId(){
        assertNotEquals(testCard.getId(), 10);
        testCard.setId((long) 10);
        assertEquals(testCard.getId(), 10);
    }

    @Test
    public void setChosenWord(){
        testCard.setWordList(wordList);
        Long chosenId = (long) 4;
        testCard.setChosenWord(chosenId);

        assertTrue(testCard.getWordList().get(3).getChosen());
    }
}