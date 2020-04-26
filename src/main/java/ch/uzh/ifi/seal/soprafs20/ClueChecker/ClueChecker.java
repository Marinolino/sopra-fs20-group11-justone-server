package ch.uzh.ifi.seal.soprafs20.ClueChecker;

import ch.uzh.ifi.seal.soprafs20.entity.Game.Clue;
import ch.uzh.ifi.seal.soprafs20.entity.Game.Game;

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
    public static boolean checkClue(String clueInput, Game game) throws IOException {


        List<String> stringClues = new ArrayList<>();

        //add all clues that are already saved in the game
        for (Clue existingClue : game.getClues()){
            stringClues.add(existingClue.getClue());
        }
        for (String clue: stringClues){
            //check if the clue already exists
            if (checkForDuplicate(clueInput, clue)){
                return false;
            }
            //check if the clue is equal to the chosen word
            if (game.getChosenWord().equals(clue)){
                return false;
            }
            //TODO: check invalid words
            URL obj = new URL("https://api.datamuse.com/words?sp=prince");

            String str = ClueChecker.makeRequest(game.getChosenWord());
            ArrayList<String> wordList = ClueChecker.makeList(str);
            System.out.println("-CONTENT BEGIN-");
            System.out.println(wordList);
            System.out.println("-CONTENT END-");
            for (int i = 0; i<wordList.size(); i++) {
                if (wordList.get(i).equalsIgnoreCase("\"" + clueInput + "\"")) {
                    return false;
                }
            }
            return true;
        }
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
    private static String makeRequest(String chosenWord) throws IOException {
        String response;
        String urlString = "https://api.datamuse.com/words?sp=" + chosenWord;
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
            if ((i-1)%4 == 0) {
                finalList.add(tempList[i]);
            }
        }
        System.out.println(finalList);
        return finalList;
    }

    private static String makeRequestBackup() throws IOException {
        String response;
        URL url = new URL("https://api.datamuse.com/words?sp=prince");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        //String inputLine;
        StringBuffer content = new StringBuffer();
        response =  content.append(in.readLine()).toString();

        // while ((inputLine = in.readLine()) != null) {
        //content.append(inputLine);
        // }
        in.close();
        //response = content.toString();
        return response;
    }
}
