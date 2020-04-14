package ch.uzh.ifi.seal.soprafs20.rest.dto;

import java.util.List;

public class CluePostDTO {

    private List<String> clues;

    public List<String> getClues(){
        return clues;
    }

    public void setClues(List<String> clues){
        this.clues = clues;
    }
}
