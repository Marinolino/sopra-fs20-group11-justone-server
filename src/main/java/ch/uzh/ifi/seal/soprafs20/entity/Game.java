package ch.uzh.ifi.seal.soprafs20.entity;

import ch.uzh.ifi.seal.soprafs20.Game.Card;
import ch.uzh.ifi.seal.soprafs20.Game.Clue;
import ch.uzh.ifi.seal.soprafs20.Game.Deck;
import ch.uzh.ifi.seal.soprafs20.Game.GameBox;
import ch.uzh.ifi.seal.soprafs20.constant.GameStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;

@Entity
@Table(name = "GAME")
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private GameStatus status;

    @Column(nullable = false)
    private boolean normalMode;

    @Column(nullable = false)
    private int score;

    @Transient
    private GameBox gameBox;

    @Transient
    private Deck deck;

    @Transient
    private Deck correctlyGuessed;

    @Transient
    private ArrayList<User> users;

    @Transient
    private ArrayList<Clue> clues;

    @Transient
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

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    //add one user to the user list
    public void addUser(User user){
        this.users.add(user);
    }

    public ArrayList<Clue> getClues() {
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
