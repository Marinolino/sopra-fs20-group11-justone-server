package ch.uzh.ifi.seal.soprafs20.entity.game;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "gameBox")
public class GameBox implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @OneToOne(mappedBy = "gameBox")
    private Game game;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "gameBox_id")
    private List<Card> cardList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game){
        this.game = game;
    }

    public void addCard(Card card){
        cardList.add(card);
        card.setGameBox(this);
    }

    public List<Card> getCards(){
        return this.cardList;
    }

    public void setCards(List<Card> cardList){
        this.cardList = cardList;
    }
}