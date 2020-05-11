package ch.uzh.ifi.seal.soprafs20.rest.dto;

import ch.uzh.ifi.seal.soprafs20.constant.GuessStatus;

public class GuessGetDTO {

    private String guessWord;

    private GuessStatus guessStatus;

    private int time;

    public String getGuessWord(){
        return guessWord;
    }

    public void setGuessWord(String guessWord){
        this.guessWord = guessWord;
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
