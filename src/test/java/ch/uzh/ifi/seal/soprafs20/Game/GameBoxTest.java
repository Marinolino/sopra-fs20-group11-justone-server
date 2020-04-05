package ch.uzh.ifi.seal.soprafs20.Game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class GameBoxTest {

    ArrayList<MysteryWord> wordList = new ArrayList<MysteryWord>();
    Card testCard;
    Card testCard2;
    GameBox testBox;


    @BeforeEach
    public void setup(){
        wordList.add(new MysteryWord("Test1"));
        wordList.add(new MysteryWord("Test2"));
        wordList.add(new MysteryWord("Test3"));
        wordList.add(new MysteryWord("Test4"));
        wordList.add(new MysteryWord("Test5"));
        testCard = new Card(1, wordList);

        wordList.add(new MysteryWord("TestA"));
        wordList.add(new MysteryWord("TestB"));
        wordList.add(new MysteryWord("TestC"));
        wordList.add(new MysteryWord("TestD"));
        wordList.add(new MysteryWord("TestE"));
        testCard2 = new Card(2, wordList);

        testBox = new GameBox();
    }

    @Test
    public void newBoxIsEmpty(){
        ArrayList<Card> cards = testBox.getCards();

        assertEquals(cards.size(), 0);
    }


    @Test
    public void addCardToBox(){
        testBox.addCard(testCard);
        ArrayList<Card> cards = testBox.getCards();

        assertEquals(cards.size(), 1);
    }

    @Test
    public void removeCardFromBox_CardRemoved(){
        testBox.addCard(testCard);
        testBox.addCard(testCard2);
        ArrayList<Card> cardsBefore = testBox.getCards();

        testBox.removeCard(testCard.getId());
        ArrayList<Card> cardsAfter= testBox.getCards();

        assertEquals(cardsBefore.size(), 1);
        assertEquals(cardsAfter.size(), 1);
    }

    @Test
    public void removeCardFromBox_NoCardRemoved(){
        testBox.addCard(testCard);
        ArrayList<Card> cardsBefore = testBox.getCards();

        testBox.removeCard(testCard2.getId());
        ArrayList<Card> cardsAfter= testBox.getCards();

        assertEquals(cardsBefore.size(), 1);
        assertEquals(cardsAfter.size(), 1);
    }
}