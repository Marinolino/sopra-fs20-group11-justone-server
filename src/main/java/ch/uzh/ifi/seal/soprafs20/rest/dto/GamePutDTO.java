package ch.uzh.ifi.seal.soprafs20.rest.dto;

public class GamePutDTO {

    private Long currentUserId;

    private int score;

    public Long getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(Long currentUserId) {
        this.currentUserId = currentUserId;
    }

    public int getScore(){
        return this.score;
    }

    public void setScore(int score){
        this.score = score;
    }
}
