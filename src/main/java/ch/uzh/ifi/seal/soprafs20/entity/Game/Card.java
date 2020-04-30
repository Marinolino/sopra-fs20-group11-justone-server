package ch.uzh.ifi.seal.soprafs20.entity.Game;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "card")
public class Card implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    @ElementCollection
    private List<String> mysteryWords = new ArrayList<>();

    @OneToOne(mappedBy = "activeCard")
    private Game game;

    @ManyToOne
    @JoinColumn(name="gameBox_id", insertable = false, updatable = false)
    private GameBox gameBox;

    @ManyToOne
    @JoinColumn(name="deck_id", insertable = false, updatable = false)
    private Deck deck;

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public int getScore(){
        return this.score;
    }

    public void setScore(int score){
        this.score = score;
    }

    public void addScore(int score){
        this.score += score;
    }

    public List<String> getMysteryWords(){
        return this.mysteryWords;
    }

    public void setMysteryWords(List<String> mysteryWords){
        this.mysteryWords = mysteryWords;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game){
        this.game = game;
    }

    public GameBox getGameBox() {
        return gameBox;
    }

    public void setGameBox(GameBox gameBox){
        this.gameBox = gameBox;
    }

    public Deck getGDeck() {
        return deck;
    }

    public void setDeck(Deck deck){
        this.deck = deck;
    }
}
