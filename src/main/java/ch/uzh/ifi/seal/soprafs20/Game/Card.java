package ch.uzh.ifi.seal.soprafs20.Game;

import ch.uzh.ifi.seal.soprafs20.entity.Game;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Card {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "card")
    private List<MysteryWord> wordList = new ArrayList<MysteryWord>();

    @OneToOne
    public Game game;

    @ManyToOne
    public GameBox gameBox;

    @ManyToOne
    public Deck deck;

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public List<MysteryWord> getWords(){
        return this.wordList;
    }

    public void setWords(List<MysteryWord> wordList){
        this.wordList = wordList;
    }
}
