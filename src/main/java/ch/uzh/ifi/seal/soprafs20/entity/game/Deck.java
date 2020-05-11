package ch.uzh.ifi.seal.soprafs20.entity.game;

import ch.uzh.ifi.seal.soprafs20.exceptions.SopraServiceException;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "deck")
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
    private List<Card> cardList = new ArrayList<>();

    @OneToOne(mappedBy = "deck")
    private Game game;


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
        if (!cardList.isEmpty() && !hasNext){
            hasNext = true;
        }
        for (Card card : cardList){
            card.setDeck(this);
        }
    }

    //add a card at the top of the deck
    public void addCard(Card card){
        cardList.add(0, card);
        if (!hasNext) {
            hasNext = true;
        }
        card.setDeck(this);
    }

    //returns the top card of the deck, throws an exception if the deck is empty
    public Card getTopCard(){
        if (hasNext){
            Card card = cardList.remove(0);
            card.setDeck(null);
            if (cardList.isEmpty()){
                hasNext = false;
            }
            return card;
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
