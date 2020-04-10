package ch.uzh.ifi.seal.soprafs20.entity.Game;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Card implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
    private List<MysteryWord> wordList = new ArrayList<MysteryWord>();

    @OneToOne(cascade = CascadeType.ALL)
    public Game game;

    @ManyToOne(cascade = CascadeType.ALL)
    public GameBox gameBox;

    @ManyToOne(cascade = CascadeType.ALL)
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
