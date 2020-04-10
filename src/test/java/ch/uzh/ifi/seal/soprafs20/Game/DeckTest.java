package ch.uzh.ifi.seal.soprafs20.Game;

import ch.uzh.ifi.seal.soprafs20.entity.Game.Card;
import ch.uzh.ifi.seal.soprafs20.entity.Game.Deck;
import ch.uzh.ifi.seal.soprafs20.exceptions.SopraServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    Deck testDeck;
    Card testCard;

    @BeforeEach
    public void setup() {
        testDeck = new Deck();
        testCard = new Card();
    }

    @Test
    public void addACard() {
        testDeck.addCard(testCard);

        assertEquals(testDeck.deckSize(), 1);
    }

    @Test
    public void drawCard_emptyDeck() throws Exception {
        String message = "The deck is empty!";
        SopraServiceException exception = exception = assertThrows(SopraServiceException.class, () -> testDeck.getTopCard(), message);

        assertEquals(message, exception.getMessage());
    }

    @Test
    public void drawCard_success() {
        testDeck.addCard(testCard);

        assertEquals(testDeck.getTopCard(), testCard );
        assertEquals(testDeck.deckSize(), 0);
    }

    @Test
    public void setId(){
        assertNotEquals(testDeck.getId(), 2);

        testDeck.setId((long)2);

        assertEquals(testDeck.getId(), 2);
    }
}