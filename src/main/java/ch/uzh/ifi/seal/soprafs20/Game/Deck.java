package ch.uzh.ifi.seal.soprafs20.Game;

import ch.uzh.ifi.seal.soprafs20.entity.Game;
import ch.uzh.ifi.seal.soprafs20.exceptions.SopraServiceException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Deck {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private boolean hasNext;

    @OneToMany(mappedBy = "deck")
    private List<Card> cardList = new ArrayList<Card>();

    @OneToOne
    public Game game;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //add a card at the top of the deck
    public void addCard(Card card){
        cardList.add(0, card);
        if (!hasNext)
        hasNext = true;
    }

    //returns the top card of the deck, throws an exception if the deck is empty
    public Card getTopCard(){
        if (hasNext){
            Card topCard = cardList.remove(0);
            if (cardList.size() == 0){
                hasNext = false;
            }
            return topCard;
        }
        else{
            String message = "The deck is empty!";
            throw new SopraServiceException(message);
        }
    }

    public int deckSize(){
        return cardList.size();
    }
}
