package ch.uzh.ifi.seal.soprafs20.rest.dto;

import java.util.ArrayList;
import java.util.List;

public class CluesGetDTO {

    private List<String> clues = new ArrayList<>();

    private boolean allAutomaticClues;

    private boolean allManualClues;

    public List<String> getClues() {
        return clues;
    }

    public void setClues(List<String> clues) {
        this.clues = clues;
    }

    public void addAClue(String clue){
        this.clues.add(clue);
    }

    public boolean getAllAutomaticClues(){
        return allAutomaticClues;
    }

    public void setAllAutomaticClues(boolean allAutomaticClues){
        this.allAutomaticClues = allAutomaticClues;
    }

    public boolean getAllManualClues() {
        return allManualClues;
    }

    public void setAllManualClues(boolean allManualClues) {
        this.allManualClues = allManualClues;
    }
}

