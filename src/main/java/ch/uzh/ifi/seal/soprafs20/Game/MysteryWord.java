package ch.uzh.ifi.seal.soprafs20.Game;

public class MysteryWord {

    private String word;
    private boolean chosen;

    public MysteryWord(String word){
        this.word = word;
        this.chosen = false;
    }

    public String getWord(){
        return this.word;
    }

    public boolean getChosen(){
        return this.chosen;
    }

    public void setChosen(boolean chosen){
        this.chosen = chosen;
    }
}
