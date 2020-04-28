package ch.uzh.ifi.seal.soprafs20.entity.Game;

import ch.uzh.ifi.seal.soprafs20.constant.ClueStatus;
import ch.uzh.ifi.seal.soprafs20.constant.GameStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "game")
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private GameStatus status;

    @Column(nullable = false)
    private boolean normalMode;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private int round;

    @Column(nullable = false)
    private Long currentUserId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gameBox_id", referencedColumnName = "id")
    private GameBox gameBox;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "deck_id", referencedColumnName = "id")
    private Deck deck;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "correctlyGuessed_id", referencedColumnName = "id")
    private Deck correctlyGuessed;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_id", referencedColumnName = "id")
    private Card activeCard;

    @Column
    private String chosenWord;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id")
    private List<Clue> clues = new ArrayList<Clue>();

    @Column(nullable = false)
    @ElementCollection
    private List<Long> userIds = new ArrayList<>();

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

    public void addScore(int score) {
        this.score += score;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void addRound(){
        this.round += 1;
    }

    public Long getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(Long currentUserId) {
        this.currentUserId = currentUserId;
    }

    public GameBox getGameBox() {
        return gameBox;
    }

    public void setGameBox(GameBox gameBox) {
        this.gameBox = gameBox;
        gameBox.setGame(this);
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
        deck.setGame(this);
    }

    public Deck getCorrectlyGuessed() {
        return correctlyGuessed;
    }

    public void setCorrectlyGuessed(Deck correctlyGuessed) {
        this.correctlyGuessed = correctlyGuessed;
        correctlyGuessed.setGame(this);
    }

    //add one card at the top off the correctly guessed pile
    public void addToCorrectlyGuessed(Card card) {
        this.correctlyGuessed.addCard(card);
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    //add one user to the user list
    public void addUserId(Long id){
        this.userIds.add(id);
    }

    public void removeUserId(Long id) {
        this.userIds.remove(id);
    }

    public List<Clue> getClues() {
        return clues;
    }

    public void setClueToInvalid(String name) {
        for(Clue clue : clues){
            if (name.equals(clue.getClue())){
                clue.setValid(ClueStatus.INVALID);
            }
        }
    }

    public void setClues(List<Clue> clues) {
        this.clues = clues;
        if (clues != null){
            for (Clue clue : clues){
                clue.setGame(this);
            }
        }

    }

    //add one clue to the clue list
    public void addClue(Clue clue){
        this.clues.add(clue);
        clue.setGame(this);
    }

    public Card getActiveCard() {
        return activeCard;
    }

    public void setActiveCard(Card activeCard) {
        this.activeCard = activeCard;
        if (activeCard != null){
            activeCard.setGame(this);
        }
    }

    //get's the top card from the deck and sets it as the active card
    public void setActiveCardFromDeck(){
        this.setActiveCard(this.deck.getTopCard());
        activeCard.setGame(this);
    }

    public String getChosenWord() {
        return chosenWord;
    }

    public void setChosenWord(String chosenWord){
        this.chosenWord = chosenWord;
    }
}
