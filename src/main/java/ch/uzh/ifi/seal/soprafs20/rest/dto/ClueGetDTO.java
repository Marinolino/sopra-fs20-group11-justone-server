package ch.uzh.ifi.seal.soprafs20.rest.dto;

import java.util.ArrayList;
import java.util.List;

public class ClueGetDTO {

    private List<String> clues = new ArrayList<>();

    public List<String> getClues() {
        return clues;
    }

    public void setClues(List<String> clues) {
        this.clues = clues;
    }

    public void addAClue(String clue){
        this.clues.add(clue);
    }
}

