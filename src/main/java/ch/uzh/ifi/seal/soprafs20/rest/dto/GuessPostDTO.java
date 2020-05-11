package ch.uzh.ifi.seal.soprafs20.rest.dto;

public class GuessPostDTO {

    private String guessWord;

    private int time;

    public String getGuessWord() {
        return guessWord;
    }

    public void setGuessWord(String guessWord) {
        this.guessWord = guessWord;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
