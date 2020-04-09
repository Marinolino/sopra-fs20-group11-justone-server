package ch.uzh.ifi.seal.soprafs20.Game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameBoxTest {

    List<MysteryWord> wordList1 = new ArrayList<MysteryWord>();
    List<MysteryWord> wordList2 = new ArrayList<MysteryWord>();
    Card testCard1;
    Card testCard2;
    GameBox testBox;


    //create 2 new cards
    @BeforeEach
    public void setup(){
        testCard1 = new Card();
        testCard2 = new Card();

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

        wordList1.add(mysteryWord1);
        wordList1.add(mysteryWord2);
        wordList1.add(mysteryWord3);
        wordList1.add(mysteryWord4);
        wordList1.add(mysteryWord5);
        testCard1.setWords(wordList1);

        mysteryWord1.setWord("TestA");
        mysteryWord2.setWord("TestB");
        mysteryWord3.setWord("TestC");
        mysteryWord4.setWord("TestD");
        mysteryWord5.setWord("TestE");

        wordList2.add(mysteryWord1);
        wordList2.add(mysteryWord2);
        wordList2.add(mysteryWord3);
        wordList2.add(mysteryWord4);
        wordList2.add(mysteryWord5);
        testCard2.setWords(wordList2);

        testBox = new GameBox();
    }

    @Test
    public void newBoxIsEmpty(){
        List<Card> cards = testBox.getCards();

        assertEquals(cards.size(), 0);
    }


    @Test
    public void addCardToBox(){
        testBox.addCard(testCard1);
        List<Card> cards = testBox.getCards();

        assertEquals(cards.size(), 1);
    }
}