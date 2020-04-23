package ch.uzh.ifi.seal.soprafs20.Game;

import ch.uzh.ifi.seal.soprafs20.entity.Game.Card;
import ch.uzh.ifi.seal.soprafs20.entity.Game.GameBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameBoxTest {

    List<String> wordList1 = new ArrayList<>();
    List<String> wordList2 = new ArrayList<>();
    Card testCard1;
    Card testCard2;
    GameBox testBox;


    //create 2 new cards
    @BeforeEach
    public void setup(){
        testCard1 = new Card();
        testCard2 = new Card();

        wordList1.add("Test1");
        wordList1.add("Test2");
        wordList1.add("Test3");
        wordList1.add("Test4");
        wordList1.add("Test5");
        testCard1.setMysteryWords(wordList1);

        wordList2.add("TestA");
        wordList2.add("TestB");
        wordList2.add("TestC");
        wordList2.add("TestD");
        wordList2.add("TestE");
        testCard2.setMysteryWords(wordList2);

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

    @Test
    public void setCards(){
        List<Card> cards = new ArrayList<Card>();
        cards.add(testCard1);
        cards.add(testCard2);
        testBox.setCards(cards);

        assertEquals(testBox.getCards(), cards);
    }

    @Test
    public void getId(){
        assertNotEquals(testBox.getId(), 8);
        testBox.setId((long) 8);
        assertEquals(testBox.getId(), 8);
    }
}