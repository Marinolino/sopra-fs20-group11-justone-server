package ch.uzh.ifi.seal.soprafs20.entity.Game;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "clue")
public class Clue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String clue;

    @Column(nullable = false)
    private boolean valid;

    @ManyToOne
    @JoinColumn(name="game_id", insertable = false, updatable = false)
    private Game game;

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
        return valid;
    }

    public void setValid(boolean valid){
        this.valid = valid;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game){
        this.game = game;
    }
}
