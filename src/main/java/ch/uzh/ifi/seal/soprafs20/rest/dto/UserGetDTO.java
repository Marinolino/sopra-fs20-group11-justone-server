package ch.uzh.ifi.seal.soprafs20.rest.dto;

import ch.uzh.ifi.seal.soprafs20.constant.UserStatus;

public class UserGetDTO {

    private Long id;

    private String name;

    private String username;

    private UserStatus status;

    private int correctlyGuessed;

    private int duplicateClues;

    private int score;

    private int gamesPlayed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public int getCorrectlyGuessed(){
        return this.correctlyGuessed;
    }

    public void setCorrectlyGuessed(int correctlyGuessed){
        this.correctlyGuessed = correctlyGuessed;
    }

    public int getDuplicateClues(){
        return this.duplicateClues;
    }

    public void setDuplicateClues(int duplicateClues){
        this.duplicateClues = duplicateClues;
    }

    public int getScore(){
        return this.score;
    }

    public void setScore(int score){
        this.score = score;
    }

    public int getGamesPlayed(){
        return this.gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed){
        this.gamesPlayed = gamesPlayed;
    }
}
