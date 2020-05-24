package ch.uzh.ifi.seal.soprafs20.cluechecker;

import ch.uzh.ifi.seal.soprafs20.constant.ClueStatus;
import ch.uzh.ifi.seal.soprafs20.entity.game.Clue;
import ch.uzh.ifi.seal.soprafs20.entity.game.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public final class ClueChecker {

    private ClueChecker(){
    }

    public static Clue checkClue(Clue clueInput, Game game) throws IOException {

        List<String> stringClues = new ArrayList<>();

        //check if the clue input is valid
        if (clueInput.getClueWord() == null || clueInput.getClueWord().isBlank() || clueInput.getClueWord().contains(" ") || clueInput.getClueWord().equals("OVERTIMED")) {
            clueInput.setValid(ClueStatus.INVALID);
            return clueInput;
        }
        //add all clues that are already saved in the game
        for (Clue existingClue : game.getClues()){
            stringClues.add(existingClue.getClueWord());
        }
        for (String clue: stringClues) {
            //check if the clue already exists
            if (checkForDuplicate(clueInput.getClueWord(), clue)) {
                clueInput.setValid(ClueStatus.DUPLICATE);
                return clueInput;
            }
        }
        //check if the clue is equal to the chosen word
        if (game.getChosenWord().equals(clueInput.getClueWord())){
            clueInput.setValid(ClueStatus.INVALID);
            return clueInput;
        }
        //check if clue is contained in chosenWord or vice versa
        if (checkIfClueOrWordSubstring(clueInput.getClueWord(), game.getChosenWord())) {
            clueInput.setValid((ClueStatus.INVALID));
            return clueInput;
        }
        //create list of homophones
        String str = ClueChecker.makeRequest(game.getChosenWord());
        ArrayList<String> wordList = ClueChecker.makeList(str);
        //check if clue is homophone of chosenword
        for (int i = 0; i<wordList.size(); i++) {
            if (wordList.get(i).equalsIgnoreCase("\"" + clueInput.getClueWord() + "\"")) {
                clueInput.setValid(ClueStatus.INVALID);
                return clueInput;
            }
        }
        //check if clue is plural of chosenword or vice versa.
        if (clueInput.getClueWord().equalsIgnoreCase(game.getChosenWord() + "s") ||
                game.getChosenWord().equalsIgnoreCase(clueInput.getClueWord() + "s")) {
                clueInput.setValid(ClueStatus.INVALID);
                return clueInput;
        }
        clueInput.setValid(ClueStatus.VALID);
        return clueInput;
    }

    //checks if the clue already exists
    //returns true if the clues are duplicates, false otherwise
    private static boolean checkForDuplicate(String newClue, String existingClue){
        //remove all whitespaces
        newClue = newClue.replaceAll("\\s+","");
        existingClue = existingClue.replaceAll("\\s+","");

        //changes both strings to lowercase and compares them
        return newClue.equalsIgnoreCase(existingClue);
    }
    //
    private static boolean checkIfClueOrWordSubstring(String newClue, String chosenWord){
        return chosenWord.toLowerCase().contains(newClue.toLowerCase()) || newClue.toLowerCase().contains(chosenWord.toLowerCase());
    }

    private static String makeRequest(String chosenWord) throws IOException {
        String response;
        String urlString = "https://api.datamuse.com/words?rel_hom=" + chosenWord;
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder content = new StringBuilder();
        response =  content.append(in.readLine()).toString();
        in.close();
        return response;
    }

    private static ArrayList<String> makeList(String rawList) {
        String[] tempList;
        ArrayList<String> finalList = new ArrayList<>();
        tempList = rawList.split(":|\\,");
        for (int i = 0; i < tempList.length; i ++) {
            if ((i-1)%6 == 0) {
                finalList.add(tempList[i]);
            }
        }
        return finalList;
    }
}
