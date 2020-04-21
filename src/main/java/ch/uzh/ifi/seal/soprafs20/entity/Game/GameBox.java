package ch.uzh.ifi.seal.soprafs20.entity.Game;

import ch.uzh.ifi.seal.soprafs20.entity.Game.Card;
import ch.uzh.ifi.seal.soprafs20.entity.Game.Game;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
<<<<<<< Updated upstream
=======
@Table(name = "gameBox")
>>>>>>> Stashed changes
public class GameBox implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

<<<<<<< Updated upstream
    @OneToOne(cascade = CascadeType.ALL)
    public Game game;
=======
    @OneToOne(mappedBy = "gameBox")
    private Game game;
>>>>>>> Stashed changes

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "gameBox_id")
    private List<Card> cardList = new ArrayList<Card>();

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