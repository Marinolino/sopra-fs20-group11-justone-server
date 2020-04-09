package ch.uzh.ifi.seal.soprafs20.rest.dto;

import ch.uzh.ifi.seal.soprafs20.Game.Card;
import ch.uzh.ifi.seal.soprafs20.Game.Clue;
import ch.uzh.ifi.seal.soprafs20.Game.Deck;
import ch.uzh.ifi.seal.soprafs20.Game.GameBox;
import ch.uzh.ifi.seal.soprafs20.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs20.entity.User;

import java.util.ArrayList;
import java.util.List;

public class GamePutDTO {

    private Long id;

    private String token;

    private GameStatus status;

    private boolean normalMode;

    private int score;

    private GameBox gameBox;

    private Deck deck;

    private Deck correctlyGuessed;

    private List<User> users;

    private List<Clue> clues;

    private Card activeCard;

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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public GameBox getGameBox() {
        return gameBox;
    }

    public void setGameBox(GameBox gameBox) {
        this.gameBox = gameBox;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Deck getCorrectlyGuessed() {
        return correctlyGuessed;
    }

    public void setCorrectlyGuessed(Deck correctlyGuessed) {
        this.correctlyGuessed = correctlyGuessed;
    }

    //add one card at the top off the correctly guessed pile
    public void addToCorrectlyGuessed(Card card) {
        this.correctlyGuessed.addCard(card);
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    //add one user to the user list
    public void addUser(User user){
        this.users.add(user);
    }

    public List<Clue> getClues() {
        return clues;
    }

    public void setClues(ArrayList<Clue> clues) {
        this.clues = clues;
    }

    //add one clue to the clue list
    public void addClue(Clue clue){
        this.clues.add(clue);
    }

    public Card getActiveCard() {
        return activeCard;
    }

    public void setActiveCard(Card activeCard) {
        this.activeCard = activeCard;
    }


}
