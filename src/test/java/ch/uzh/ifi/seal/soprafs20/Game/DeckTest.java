package ch.uzh.ifi.seal.soprafs20.Game;

import ch.uzh.ifi.seal.soprafs20.entity.game.Card;
import ch.uzh.ifi.seal.soprafs20.entity.game.Deck;
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

        assertEquals(1, testDeck.deckSize());
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
        assertEquals(0, testDeck.deckSize());
    }

    @Test
    public void setId(){
        assertNotEquals(2, testDeck.getId());

        testDeck.setId((long)2);

        assertEquals(2, testDeck.getId());
    }
}