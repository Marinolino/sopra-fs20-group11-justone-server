package ch.uzh.ifi.seal.soprafs20.Game;

public class Clue {
    private String clue;
    private int clueId;
    private boolean valid;

    public Clue(String clue, int id){
        this.clue = clue;
        this.clueId = id;
        this.valid = false;
    }

    public String getClue(){
        return this.clue;
    }

    public void setClue(String clue){
        this.clue = clue;
    }

    public int getId(){
        return this.clueId;
    }

    public void setId(int clueId){
        this.clueId = clueId;
    }

    public boolean getValid(){
        return this.valid;
    }

    public void setValid(boolean valid){
        this.valid = valid;
    }
}
