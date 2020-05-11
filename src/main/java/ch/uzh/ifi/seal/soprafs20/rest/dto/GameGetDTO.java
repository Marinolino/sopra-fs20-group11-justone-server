package ch.uzh.ifi.seal.soprafs20.rest.dto;

import ch.uzh.ifi.seal.soprafs20.constant.ChosenWordStatus;
import ch.uzh.ifi.seal.soprafs20.constant.GameStatus;

import java.util.List;

public class GameGetDTO {

    private Long id;

    private String token;

    private GameStatus status;

    private boolean normalMode;

    private boolean changeWord;

    private int score;

    private int round;

    private int deckSize;

    private String chosenWord;

    private ChosenWordStatus wordStatus;

    private Long currentUserId;

    private List<Long> userIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public boolean getNormalMode() {
        return normalMode;
    }

    public void setNormalMode(boolean mode) {
        this.normalMode = mode;
    }

    public boolean getChangeWord() {
        return changeWord;
    }

    public void setChangeWord(boolean changeWord) {
        this.changeWord = changeWord;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getDeckSize() {
        return deckSize;
    }

    public void setDeckSize(int deckSize) {
        this.deckSize = deckSize;
    }

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

    public Long getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(Long currentUserId) {
        this.currentUserId = currentUserId;
    }

    public List<Long> getUsersIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    //add one user to the user list
    public void addUserId(Long id){
        this.userIds.add(id);
    }
}


