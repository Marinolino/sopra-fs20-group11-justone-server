package ch.uzh.ifi.seal.soprafs20.ClueChecker;

import ch.uzh.ifi.seal.soprafs20.constant.ClueStatus;
import ch.uzh.ifi.seal.soprafs20.entity.Game.Clue;
import ch.uzh.ifi.seal.soprafs20.entity.Game.Game;
import ch.uzh.ifi.seal.soprafs20.exceptions.API.POST.PostRequestException409;
import org.springframework.http.HttpStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class ClueChecker {
    public static Clue checkClue(Clue clueInput, Game game) throws IOException {

        List<String> stringClues = new ArrayList<>();

        //check if the clue input is valid
        if (clueInput.getClue() == null || clueInput.getClue().isBlank() || clueInput.getClue().contains(" ")) {
            clueInput.setValid(ClueStatus.INVALID);
            return clueInput;
        }
        //add all clues that are already saved in the game
        for (Clue existingClue : game.getClues()){
            stringClues.add(existingClue.getClue());
        }
        for (String clue: stringClues) {
            //check if the clue already exists
            if (checkForDuplicate(clueInput.getClue(), clue)) {
                clueInput.setValid(ClueStatus.DUPLICATE);
                return clueInput;
            }
        }
        //check if the clue is equal to the chosen word
        if (game.getChosenWord().equals(clueInput.getClue())){
            clueInput.setValid(ClueStatus.INVALID);
            return clueInput;
        }
        //create list of homophones
        String str = ClueChecker.makeRequest(game.getChosenWord());
        ArrayList<String> wordList = ClueChecker.makeList(str);
        //check if clue is homophone of chosenword
        for (int i = 0; i<wordList.size(); i++) {
            if (wordList.get(i).equalsIgnoreCase("\"" + clueInput.getClue() + "\"")) {
                clueInput.setValid(ClueStatus.INVALID);
                return clueInput;
            }
        }
        //check if clue is plural of chosenword or vice versa.
        if (clueInput.getClue().equalsIgnoreCase(game.getChosenWord() + "s") ||
                game.getChosenWord().equalsIgnoreCase(clueInput.getClue() + "s")) {
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
        if (newClue.toLowerCase().equals(existingClue.toLowerCase())){
            return true;
        }
        return false;
    }

    private static String makeRequest(String chosenWord) throws IOException {
        String response;
        String urlString = "https://api.datamuse.com/words?rel_hom=" + chosenWord;
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuffer content = new StringBuffer();
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
