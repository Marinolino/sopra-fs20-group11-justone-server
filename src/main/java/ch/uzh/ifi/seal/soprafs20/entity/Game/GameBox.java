package ch.uzh.ifi.seal.soprafs20.entity.Game;

import ch.uzh.ifi.seal.soprafs20.entity.Game.Card;
import ch.uzh.ifi.seal.soprafs20.entity.Game.Game;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "GAMEBOX")
public class GameBox implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="game_id")
    public Game game;

    @OneToMany(mappedBy = "gameBox", cascade = CascadeType.ALL)
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