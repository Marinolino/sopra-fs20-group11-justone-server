package ch.uzh.ifi.seal.soprafs20.rest.dto;

import ch.uzh.ifi.seal.soprafs20.constant.ClueStatus;

public class ClueGetDTO {

    private String clue;

    private int time;

    private ClueStatus valid;

    public String getClue() {
        return clue;
    }

    public void setClue(String clue) {
        this.clue = clue;
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
