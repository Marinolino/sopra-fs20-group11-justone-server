package ch.uzh.ifi.seal.soprafs20.rest.dto;

import ch.uzh.ifi.seal.soprafs20.constant.GuessStatus;

import javax.persistence.Column;

public class GuessGetDTO {

    private String guess;

    private GuessStatus guessStatus;

    private int time;

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

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
