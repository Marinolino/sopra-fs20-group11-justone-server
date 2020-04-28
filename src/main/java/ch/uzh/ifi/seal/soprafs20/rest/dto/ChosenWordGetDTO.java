package ch.uzh.ifi.seal.soprafs20.rest.dto;

import ch.uzh.ifi.seal.soprafs20.constant.ChosenWordStatus;

public class ChosenWordGetDTO {

    private String chosenWord;

    private ChosenWordStatus wordStatus;

    public String getChosenWord() {
        return chosenWord;
    }

    public void setChosenWord(String chosenWord){
        this.chosenWord = chosenWord;
    }

    public ChosenWordStatus getWordStatus() {
        return wordStatus;
    }

    public void setWordStatus(ChosenWordStatus wordStatus){
        this.wordStatus = wordStatus;
    }
}
