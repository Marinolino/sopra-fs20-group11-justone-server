package ch.uzh.ifi.seal.soprafs20.Game;

import ch.uzh.ifi.seal.soprafs20.entity.Game;
import javax.persistence.*;

@Entity
public class Clue {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String clue;

    @Column(nullable = false)
    private boolean valid;

    @ManyToOne
    public Game game;

    public String getClue(){
        return this.clue;
    }

    public void setClue(String clue){
        this.clue = clue;
    }

    public Long getId(){
        return this.id;
    }

    public void setId(Long clueId){
        this.id = clueId;
    }

    public boolean getValid(){
        return this.valid;
    }

    public void setValid(boolean valid){
        this.valid = valid;
    }
}
