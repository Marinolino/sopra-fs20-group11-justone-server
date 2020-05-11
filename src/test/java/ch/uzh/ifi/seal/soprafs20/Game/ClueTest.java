package ch.uzh.ifi.seal.soprafs20.Game;

import ch.uzh.ifi.seal.soprafs20.entity.game.Clue;
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

        testClue.setClueWord(clueWord);

        assertEquals(testClue.getClueWord(), clueWord);
    }

    @Test
    public void setId(){
       assertNotEquals(2, testClue.getId());

        testClue.setId((long)2);

        assertEquals(2, testClue.getId());
    }
}