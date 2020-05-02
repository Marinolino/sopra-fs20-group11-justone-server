package ch.uzh.ifi.seal.soprafs20.entity.Game;

import ch.uzh.ifi.seal.soprafs20.constant.ChosenWordStatus;
import ch.uzh.ifi.seal.soprafs20.constant.ClueStatus;
import ch.uzh.ifi.seal.soprafs20.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs20.exceptions.API.GET.GetRequestException404;
import org.springframework.http.HttpStatus;

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
    private boolean changeWord;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "guess_id", referencedColumnName = "id")
    private Guess guess;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private int round;

    @Column(nullable = false)
    private int deckSize;

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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id")
    private List<Clue> clues = new ArrayList<Clue>();

    @Column
    private String chosenWord;

    @Column
    private ChosenWordStatus wordStatus;

    @Column
    private int wordCounter;

    @Column
    private int manualClueCounter;

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

    public Guess getGuess(){
        return guess;
    }

    public void setGuess(Guess guess){
        this.guess = guess;
        if (guess != null){
            guess.setGame(this);
        }

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

    public int getDeckSize() {
        return deckSize;
    }

    public void setDeckSize(int deckSize) {
        this.deckSize = deckSize;
    }

    public void updateDeckSizeFromDeck(){
        this.deckSize = deck.deckSize();
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

    public void addCardToGameBox(Card card){
        this.gameBox.addCard(card);
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
        deck.setGame(this);
        updateDeckSizeFromDeck();
    }

    public Card getTopCardFromDeck(){
        Card card = this.deck.getTopCard();
        updateDeckSizeFromDeck();
        return card;
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

    public Card getTopCardFromCorrectlyGuessed(){
        return this.correctlyGuessed.getTopCard();
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

    public Card getActiveCard() throws Exception {
        if (activeCard == null){
            throw new GetRequestException404("You need to draw a card first!", HttpStatus.NOT_FOUND);
        }
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
        updateDeckSizeFromDeck();
    }

    public void addScoreToCard(int score){
        this.activeCard.addScore(score);
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

    public int getWordCounter(){
        return wordCounter;
    }

    public void setWordCounter(int wordCounter){
        this.wordCounter = wordCounter;
    }

    public void addWordCounter(){
        wordCounter += 1;
    }

    public int getClueCounter(){
        return manualClueCounter;
    }

    public void setManualClueCounter(int manualClueCounter){
        this.manualClueCounter = manualClueCounter;
    }

    public void addManualClueCounter(){
        manualClueCounter += 1;
    }
}
