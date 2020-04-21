package ch.uzh.ifi.seal.soprafs20.entity.Game;

import ch.uzh.ifi.seal.soprafs20.exceptions.SopraServiceException;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
<<<<<<< Updated upstream
=======
@Table(name = "deck")
>>>>>>> Stashed changes
public class Deck implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private boolean hasNext;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "deck_id")
    private List<Card> cardList = new ArrayList<Card>();

<<<<<<< Updated upstream
    @OneToOne(cascade = CascadeType.ALL)
    public Game game;
=======
    @OneToOne(mappedBy = "deck")
    private Game game;
>>>>>>> Stashed changes

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
        for (Card card : cardList){
            card.setDeck(this);
        }
    }

    //add a card at the top of the deck
    public void addCard(Card card){
        cardList.add(0, card);
        if (!hasNext)
        hasNext = true;
        card.setDeck(this);
    }

    //returns the top card of the deck, throws an exception if the deck is empty
    public Card getTopCard(){
        if (hasNext){
<<<<<<< Updated upstream
            Card topCard = cardList.remove(0);
            if (cardList.size() == 0){
                hasNext = false;
            }
            return topCard;
=======
            Card card = cardList.remove(0);
            card.setDeck(null);
            if (cardList.size() == 0){
                hasNext = false;
            }
            return card;
>>>>>>> Stashed changes
        }
        else{
            throw new SopraServiceException("The deck is empty!");
        }
    }

    public int deckSize(){
        return cardList.size();
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game){
        this.game = game;
    }
}
