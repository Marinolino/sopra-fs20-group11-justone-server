package ch.uzh.ifi.seal.soprafs20.cluechecker;

import ch.uzh.ifi.seal.soprafs20.constant.ClueStatus;
import ch.uzh.ifi.seal.soprafs20.entity.game.Card;
import ch.uzh.ifi.seal.soprafs20.entity.game.Clue;
import ch.uzh.ifi.seal.soprafs20.entity.game.Game;
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
        clue1.setClueWord("A");
        testCard.setMysteryWords(wordList);
        testGame.addClue(clue1);
        testGame.setActiveCard(testCard);
        testGame.setChosenWord("house");
    }

    @Test
    public void clueAlreadyExists() throws IOException {
        testClue.setClueWord("A");
        assertEquals(ClueStatus.DUPLICATE, ClueChecker.checkClue(testClue, testGame).getValid());
    }

    @Test
    public void clueDoesNotExist() throws IOException {
        testClue.setClueWord("B");
        assertEquals(ClueStatus.VALID, ClueChecker.checkClue(testClue, testGame).getValid());
    }

    @Test
    public void clueIsNull() throws IOException {
        testClue.setClueWord(null);
        assertEquals(ClueStatus.INVALID, ClueChecker.checkClue(testClue, testGame).getValid());
    }
    @Test
    public void clueIsChosenWord() throws IOException {
        testClue.setClueWord("house");
        assertEquals(ClueStatus.INVALID, ClueChecker.checkClue(testClue, testGame).getValid());
    }

    @Test
    public void clueIsHomophone() throws IOException {
        testClue.setClueWord("haus");
        Clue checkedClue = ClueChecker.checkClue(testClue, testGame);
        assertEquals(ClueStatus.INVALID, checkedClue.getValid());
    }

    @Test
    public void clueIsPluralOfChosenWord() throws IOException {
        testClue.setClueWord("houses");
        Clue checkedClue = ClueChecker.checkClue(testClue, testGame);
        assertEquals( ClueStatus.INVALID, checkedClue.getValid());
    }

    @Test
    public void chosenWordIsPluralOfClue() throws IOException {
        testGame.setChosenWord("houses");
        testClue.setClueWord("house");
        Clue checkedClue = ClueChecker.checkClue(testClue, testGame);
        assertEquals(ClueStatus.INVALID, checkedClue.getValid());
    }
}