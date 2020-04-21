package ch.uzh.ifi.seal.soprafs20.entity.Game;

import ch.uzh.ifi.seal.soprafs20.entity.Game.Game;
import javax.persistence.*;
import java.io.Serializable;

@Entity
<<<<<<< Updated upstream
=======
@Table(name = "clue")
>>>>>>> Stashed changes
public class Clue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String clue;

<<<<<<< Updated upstream
    @ManyToOne(cascade = CascadeType.ALL)
    public Game game;
=======
    @ManyToOne
    @JoinColumn(name="game_id", insertable = false, updatable = false)
    private Game game;
>>>>>>> Stashed changes

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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game){
        this.game = game;
    }
}
