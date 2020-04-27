package ch.uzh.ifi.seal.soprafs20.entity.Game;

import ch.uzh.ifi.seal.soprafs20.constant.ClueStatus;

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
    private ClueStatus valid;

    @Column(nullable = false)
    private int time;

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

    public ClueStatus getValid(){
        return valid;
    }

    public void setValid(ClueStatus valid){
        this.valid = valid;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game){
        this.game = game;
    }

    public int getTime(){
        return time;
    }

    public void setTime(int time){
        this.time = time;
    }
}
