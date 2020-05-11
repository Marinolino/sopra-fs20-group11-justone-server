package ch.uzh.ifi.seal.soprafs20.rest.dto;

import ch.uzh.ifi.seal.soprafs20.constant.ClueStatus;

public class ClueGetDTO {

    private String clueWord;

    private int time;

    private ClueStatus valid;

    public String getClueWord() {
        return clueWord;
    }

    public void setClueWord(String clueWord) {
        this.clueWord = clueWord;
    }

    public int getTime(){
        return time;
    }

    public void setTime(int time){
        this.time = time;
    }

    public ClueStatus getValid(){
        return valid;
    }

    public void setValid(ClueStatus valid){
        this.valid = valid;
    }
}
