package ch.uzh.ifi.seal.soprafs20.ClueChecker;

import ch.uzh.ifi.seal.soprafs20.constant.ClueStatus;
import ch.uzh.ifi.seal.soprafs20.entity.Game.Card;
import ch.uzh.ifi.seal.soprafs20.entity.Game.Clue;
import ch.uzh.ifi.seal.soprafs20.entity.Game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClueCheckerTest {
    Game testGame;
    Card testCard;
    List<String> wordList;

    @BeforeEach
    public void setUp(){
        testGame = new Game();
        testCard = new Card();
        wordList = new ArrayList<>();
        wordList.add("Test1");
        wordList.add("Test2");
        wordList.add("Test3");
        wordList.add("Test4");
        wordList.add("Test5");

        Clue clue1 = new Clue();
        clue1.setClue("A");
        testCard.setMysteryWords(wordList);
        testGame.addClue(clue1);
        testGame.setActiveCard(testCard);
        testGame.setChosenWord("prince");
    }

    @Test
    public void clueAlreadyExists(){
        //assertFalse(ClueChecker.checkClue("A", testGame));
    }

    @Test
    public void clueDoesNotExists(){
        //assertTrue(ClueChecker.checkClue("B", testGame));
    }

    @Test
    public void checkClueChecker() throws IOException {
        Clue testClue = new Clue();
        testClue.setClue("price");
        Clue checkedClue = ClueChecker.checkClue(testClue, testGame);
        assertEquals(checkedClue.getValid(), ClueStatus.INVALID);
    }
}