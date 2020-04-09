package ch.uzh.ifi.seal.soprafs20.Game;

import ch.uzh.ifi.seal.soprafs20.entity.Game;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GameBox {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    public Game game;

    @OneToMany(mappedBy = "gameBox")
    private List<Card> cardList = new ArrayList<Card>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addCard(Card card){
        cardList.add(card);
    }

    public List<Card> getCards(){
        return this.cardList;
    }

    public void setCards(List<Card> cardList){
        this.cardList = cardList;
    }
}