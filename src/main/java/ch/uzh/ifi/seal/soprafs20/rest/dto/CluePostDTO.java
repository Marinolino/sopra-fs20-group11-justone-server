package ch.uzh.ifi.seal.soprafs20.rest.dto;

import java.util.List;

public class CluePostDTO {

    private String clue;

    private int time;

    public String getClue(){
        return clue;
    }

    public void setClue(String clue){
        this.clue = clue;
    }

    public int getTime(){
        return time;
    }

    public void setTime(int time){
        this.time = time;
    }
}
