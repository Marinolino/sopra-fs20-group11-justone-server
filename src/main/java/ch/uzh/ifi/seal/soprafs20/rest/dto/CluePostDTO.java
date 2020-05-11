package ch.uzh.ifi.seal.soprafs20.rest.dto;

public class CluePostDTO {

    private String clueWord;

    private int time;

    public String getClueWord(){
        return clueWord;
    }

    public void setClueWord(String clueWord){
        this.clueWord = clueWord;
    }

    public int getTime(){
        return time;
    }

    public void setTime(int time){
        this.time = time;
    }
}
