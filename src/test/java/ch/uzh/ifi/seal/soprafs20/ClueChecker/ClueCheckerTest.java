package ch.uzh.ifi.seal.soprafs20.ClueChecker;

import ch.uzh.ifi.seal.soprafs20.entity.Game.Clue;
import ch.uzh.ifi.seal.soprafs20.entity.Game.Game;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClueCheckerTest {
    Game testGame;

    @BeforeEach
    public void setUp(){
        testGame = new Game();
        Clue clue1 = new Clue();
        clue1.setClue("A");
        testGame.addClue(clue1);
    }

    @Test
    public void clueAlreadyExists(){
        assertFalse(ClueChecker.checkClue("A", testGame));
    }

    @Test
    public void clueDoesNotExists(){
        assertTrue(ClueChecker.checkClue("B", testGame));
    }
}