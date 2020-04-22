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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_id")
    private List<MysteryWord> wordList = new ArrayList<>();

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

    public List<MysteryWord> getWordList(){
        return this.wordList;
    }

    public void setWordList(List<MysteryWord> wordList){
        this.wordList = wordList;
        for (MysteryWord word : wordList){
            word.setCard(this);
        }
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

    public void setChosenWord(Long id){
        for (MysteryWord mysteryWord : wordList){
            if (id.equals(mysteryWord.getId())){
                mysteryWord.setChosen(true);
                break;
            }
        }
    }
}
