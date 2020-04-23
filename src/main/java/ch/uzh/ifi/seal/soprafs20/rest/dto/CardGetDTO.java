package ch.uzh.ifi.seal.soprafs20.rest.dto;

import java.util.ArrayList;
import java.util.List;

public class CardGetDTO {

    private List<String> words = new ArrayList<>();

    public List<String> getWords(){
        return this.words;
    }

    public void setWords(List<String> words){
        this.words = words;
    }

    public void addAWord(String word){
        this.words.add(word);
    }
}
