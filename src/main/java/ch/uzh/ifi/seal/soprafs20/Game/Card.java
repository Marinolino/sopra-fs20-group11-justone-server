package ch.uzh.ifi.seal.soprafs20.Game;

import java.util.ArrayList;

public class Card {

    private int cardId;
    private ArrayList<MysteryWord> wordList = new ArrayList<MysteryWord>();

    public Card(int cardId, ArrayList<MysteryWord> mysteryWords){
        this.cardId = cardId;
        this.wordList = mysteryWords;
    }

    public int getId(){
        return this.cardId;
    }

    public ArrayList<MysteryWord> getWords(){
        return this.wordList;
    }
}
