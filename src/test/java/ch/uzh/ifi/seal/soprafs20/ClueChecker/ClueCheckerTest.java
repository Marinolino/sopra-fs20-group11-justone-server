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
    Clue testClue = new Clue();

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
        testGame.setChosenWord("house");
    }

    @Test
    public void clueAlreadyExists() throws IOException {
        testClue.setClue("A");
        assertEquals(ClueChecker.checkClue(testClue, testGame).getValid(), ClueStatus.DUPLICATE);
    }

    @Test
    public void clueDoesNotExist() throws IOException {
        testClue.setClue("B");
        assertEquals(ClueChecker.checkClue(testClue, testGame).getValid(), ClueStatus.VALID);
    }

    @Test
    public void clueIsNull() throws IOException {
        testClue.setClue(null);
        assertEquals(ClueChecker.checkClue(testClue, testGame).getValid(), ClueStatus.INVALID);
    }
    @Test
    public void clueIsChosenWord() throws IOException {
        testClue.setClue("house");
        assertEquals(ClueChecker.checkClue(testClue, testGame).getValid(), ClueStatus.INVALID);
    }

    @Test
    public void clueIsHomophone() throws IOException {
        testClue.setClue("haus");
        Clue checkedClue = ClueChecker.checkClue(testClue, testGame);
        assertEquals(checkedClue.getValid(), ClueStatus.INVALID);
    }

    @Test
    public void clueIsPluralOfChosenWord() throws IOException {
        testClue.setClue("houses");
        Clue checkedClue = ClueChecker.checkClue(testClue, testGame);
        assertEquals(checkedClue.getValid(), ClueStatus.INVALID);
    }

    @Test
    public void chosenWordIsPluralOfClue() throws IOException {
        testGame.setChosenWord("houses");
        testClue.setClue("house");
        Clue checkedClue = ClueChecker.checkClue(testClue, testGame);
        assertEquals(checkedClue.getValid(), ClueStatus.INVALID);
    }
}