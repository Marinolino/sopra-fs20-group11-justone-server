package ch.uzh.ifi.seal.soprafs20.rest.dto;

import java.util.ArrayList;
import java.util.List;

public class CluesGetDTO {

    private List<String> clues = new ArrayList<>();

    private boolean allClues;

    public List<String> getClues() {
        return clues;
    }

    public void setClues(List<String> clues) {
        this.clues = clues;
    }

    public void addAClue(String clue){
        this.clues.add(clue);
    }

    public boolean getAllClues(){
        return allClues;
    }

    public void setAllClues(boolean allClues){
        this.allClues = allClues;
    }
}

