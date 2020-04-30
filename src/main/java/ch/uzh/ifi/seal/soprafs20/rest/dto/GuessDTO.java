package ch.uzh.ifi.seal.soprafs20.rest.dto;

import ch.uzh.ifi.seal.soprafs20.constant.GuessStatus;

public class GuessDTO {
    private String guessWord;
    private GuessStatus status;

    public GuessStatus getStatus() {
        return status;
    }

    public void setStatus(GuessStatus status) {
        this.status = status;
    }

    public String getGuessWord() {
        return guessWord;
    }

    public void setGuessWord(String guessWord) {
        this.guessWord = guessWord;
    }
}
