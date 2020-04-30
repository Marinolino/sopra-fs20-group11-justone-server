package ch.uzh.ifi.seal.soprafs20.entity.Game;

import ch.uzh.ifi.seal.soprafs20.constant.GuessStatus;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "guess")
public class Guess implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String guess;

    @Column(nullable = false)
    private GuessStatus guessStatus;

    @Column(nullable = false)
    private int time;

    @OneToOne(mappedBy = "guess")
    private Game game;

    public String getGuess(){
        return guess;
    }

    public void setGuess(String guess){
        this.guess = guess;
    }

    public GuessStatus getGuessStatus() {
        return guessStatus;
    }

    public void setGuessStatus(GuessStatus guessStatus) {
        this.guessStatus = guessStatus;
    }

    public int getTime(){
        return time;
    }

    public void setTime(int time){
        this.time = time;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game){
        this.game = game;
    }
}
