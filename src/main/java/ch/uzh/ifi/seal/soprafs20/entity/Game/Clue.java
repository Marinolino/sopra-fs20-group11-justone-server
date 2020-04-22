package ch.uzh.ifi.seal.soprafs20.entity.Game;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CLUE")
public class Clue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String clue;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="game")
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
}
