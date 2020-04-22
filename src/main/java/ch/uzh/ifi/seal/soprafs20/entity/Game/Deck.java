package ch.uzh.ifi.seal.soprafs20.entity.Game;

import ch.uzh.ifi.seal.soprafs20.entity.Game.Card;
import ch.uzh.ifi.seal.soprafs20.entity.Game.Game;
import ch.uzh.ifi.seal.soprafs20.exceptions.SopraServiceException;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Deck implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private boolean hasNext;

    @OneToMany(mappedBy = "deck", cascade = CascadeType.ALL)
    private List<Card> cardList = new ArrayList<Card>();

    @OneToOne(cascade = CascadeType.ALL)
    public Game game;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Card> getCardList() {
        return cardList;
    }

    public void setCardList(List<Card> cardList) {
        this.cardList = cardList;
        if (cardList.size() > 0 && !hasNext){
            hasNext = true;
        }
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
            throw new SopraServiceException("The deck is empty!");
        }
    }

    public int deckSize(){
        return cardList.size();
    }
}
