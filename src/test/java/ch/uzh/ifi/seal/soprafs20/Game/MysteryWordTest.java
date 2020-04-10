package ch.uzh.ifi.seal.soprafs20.Game;

import ch.uzh.ifi.seal.soprafs20.entity.Game.MysteryWord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MysteryWordTest {

    String word = "TestWord";
    MysteryWord testMysteryWord;

    @BeforeEach
    public void setup(){
        testMysteryWord = new MysteryWord();
        word = "TestWord";
        testMysteryWord.setWord(word);
    }

    @Test
    public void createMysteryWord(){
        assertEquals(testMysteryWord.getWord(), word);
        assertFalse(testMysteryWord.getChosen());

    }

    @Test
    public void setChosen(){
        testMysteryWord.setChosen(true);
        assertTrue(testMysteryWord.getChosen());
    }

    @Test
    public void getId(){
        assertNotEquals(testMysteryWord.getId(), 10);
        testMysteryWord.setId((long) 10);
        assertEquals(testMysteryWord.getId(), 10);
    }
}