package ch.uzh.ifi.seal.soprafs20.ClueChecker;

import ch.uzh.ifi.seal.soprafs20.entity.Game.Clue;
import ch.uzh.ifi.seal.soprafs20.entity.Game.Game;

import java.util.ArrayList;
import java.util.List;

public class ClueChecker {
    public static boolean checkClue(String clueInput, Game game){
        List<String> stringClues = new ArrayList<>();

        //add all clues that are already saved in the game
        for (Clue existingClue : game.getClues()){
            stringClues.add(existingClue.getClue());
        }
        //check if the clue already exists
        for (String clue: stringClues){
            if (checkForDuplicate(clueInput, clue)){
                return false;
            }
        }
        //TODO: check invalid words
        return true;
    }

    //checks if the clue already exists
    //returns true if the clues are duplicates, false otherwise
    private static boolean checkForDuplicate(String newClue, String existingClue){
        //remove all whitespaces
        newClue = newClue.replaceAll("\\s+","");
        existingClue = existingClue.replaceAll("\\s+","");

        //changes both strings to lowercase and compares them
        if (newClue.toLowerCase().equals(existingClue.toLowerCase())){
            return true;
        }
        return false;
    }
}
