package ch.uzh.ifi.seal.soprafs20.Game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    ArrayList<MysteryWord> wordList = new ArrayList<MysteryWord>();

    @BeforeEach
    public void setup(){
        wordList.add(new MysteryWord("Test1"));
        wordList.add(new MysteryWord("Test2"));
        wordList.add(new MysteryWord("Test3"));
        wordList.add(new MysteryWord("Test4"));
        wordList.add(new MysteryWord("Test5"));
    }

    @Test
    public void createNewCard(){
        Card testCard = new Card(1, wordList);

        assertEquals(testCard.getWords(), wordList);
    }

    @Test
    public void getId(){
        Card testCard = new Card(1, wordList);

        assertEquals(testCard.getId(), 1);
    }
}