package ch.uzh.ifi.seal.soprafs20.entity.Game;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CARD")
public class Card implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
    private List<MysteryWord> wordList = new ArrayList<MysteryWord>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="game")
    public Game game;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="gameBox")
    public GameBox gameBox;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="deck")
    public Deck deck;

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
