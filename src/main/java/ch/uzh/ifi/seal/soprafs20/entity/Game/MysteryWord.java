package ch.uzh.ifi.seal.soprafs20.entity.Game;

import ch.uzh.ifi.seal.soprafs20.entity.Game.Card;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "MYSTERYWORD")
public class MysteryWord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String word;

    @Column(nullable = false)
    private boolean chosen;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="card_id")
    public Card card;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
