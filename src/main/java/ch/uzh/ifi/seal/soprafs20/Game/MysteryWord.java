package ch.uzh.ifi.seal.soprafs20.Game;

import javax.persistence.*;

@Entity
public class MysteryWord {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String word;

    @Column(nullable = false)
    private boolean chosen;

    @ManyToOne
    public Card card;

    public String getWord(){
        return this.word;
    }

    public void setWord(String word){
        this.word = word;
    }

    public boolean getChosen(){
        return this.chosen;
    }

    public void setChosen(boolean chosen){
        this.chosen = chosen;
    }
}
