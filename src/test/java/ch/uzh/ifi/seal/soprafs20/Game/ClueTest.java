package ch.uzh.ifi.seal.soprafs20.Game;

import ch.uzh.ifi.seal.soprafs20.entity.Game.Clue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClueTest {

    Clue testClue = new Clue();

    @BeforeEach
    public void setup(){
        testClue = new Clue();
    }

    @Test
    public void setClue(){
        String clueWord = "TestClue";

        testClue.setClue(clueWord);

        assertEquals(testClue.getClue(), clueWord);
    }

    @Test
    public void setId(){
       assertNotEquals(testClue.getId(), 2);

        testClue.setId((long)2);

        assertEquals(testClue.getId(), 2);
    }

    @Test
    public void setValid(){
        testClue.setValid(true);
        assertTrue(testClue.getValid());
    }
}