package ch.uzh.ifi.seal.soprafs20.entity.Game;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "mysteryWord")
public class MysteryWord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String word;

    @Column(nullable = false)
    private boolean chosen;

    @ManyToOne
    @JoinColumn(name="card_id", insertable = false, updatable = false)
    private Card card;

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

    public Card getCard() {
        return card;
    }

    public void setCard(Card card){
        this.card = card;
    }

}
