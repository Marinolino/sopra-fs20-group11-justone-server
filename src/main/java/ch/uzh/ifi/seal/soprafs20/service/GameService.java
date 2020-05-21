package ch.uzh.ifi.seal.soprafs20.service;

import ch.uzh.ifi.seal.soprafs20.cluechecker.ClueChecker;
import ch.uzh.ifi.seal.soprafs20.constant.ChosenWordStatus;
import ch.uzh.ifi.seal.soprafs20.constant.GuessStatus;
import ch.uzh.ifi.seal.soprafs20.entity.game.*;
import ch.uzh.ifi.seal.soprafs20.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs20.exceptions.api.get.GetRequestException404;
import ch.uzh.ifi.seal.soprafs20.exceptions.api.post.PostRequestException409;
import ch.uzh.ifi.seal.soprafs20.exceptions.api.put.PutRequestException400;
import ch.uzh.ifi.seal.soprafs20.exceptions.api.put.PutRequestException404;
import ch.uzh.ifi.seal.soprafs20.exceptions.api.put.PutRequestException409;
import ch.uzh.ifi.seal.soprafs20.repository.*;
import ch.uzh.ifi.seal.soprafs20.rest.dto.ChosenWordPutDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class GameService {

    public static final int MAX_POINTS = 2;
    public static final int MIN_POINTS = 1;
    public static final int TIME_CLUE = 10;
    public static final int TIME_GUESS = 15;

    private final GameRepository gameRepository;
    private final GuessRepository guessRepository;
    private final ClueRepository clueRepository;

    private final Logger log = LoggerFactory.getLogger(GameService.class);

    public GameService(@Qualifier("gameRepository") GameRepository gameRepository, GuessRepository guessRepository, ClueRepository clueRepository) {
        this.gameRepository = gameRepository;
        this.guessRepository = guessRepository;
        this.clueRepository = clueRepository;
    }

    public List<Game> getGames() {
        return this.gameRepository.findAll();
    }

    public Game getGameById(Long id){
        Game gameById = gameRepository.findById(id).orElse(null);
        if (gameById == null){
            throw new GetRequestException404("No game was found!");
        }
        return gameById;
    }

    public Game createGame(Game newGame) throws FileNotFoundException {
        Game createdGame = createGameElements(newGame);
        createdGame.setToken(UUID.randomUUID().toString());
        createdGame.setStatus(GameStatus.CREATED);
        createdGame.setChangeWord(true);
        createdGame.setScore(0);
        createdGame.setRound(1);
        createdGame.setWordStatus(ChosenWordStatus.NOCHOSENWORD);
        createdGame.addUserId(createdGame.getCurrentUserId());

        // saves the given entity but data is only persisted in the database once flush() is called
        Game savedGame = gameRepository.save(createdGame);
        gameRepository.flush();

        log.debug("Created Information for Game: {}", newGame);
        return savedGame;
    }

    public Game startGame(Long id) throws PutRequestException409 {
        Game gameById = getGameById(id);

        if (gameById.getStatus() != GameStatus.CREATED){
            throw new PutRequestException409("This game has already started or finished!");
        }
        gameById.setStatus(GameStatus.RUNNING);

        //check and set if it is a game with 3 players or more than 3 players
        gameById.setNormalMode(gameById.getUserIds().size() != 3);

        // saves the given entity but data is only persisted in the database once flush() is called
        Game savedGame = gameRepository.save(gameById);
        gameRepository.flush();

        return savedGame;
    }

    public Game finishGame(Long id) throws PutRequestException409 {
        Game gameById = getGameById(id);


        if (gameById.getStatus() != GameStatus.RUNNING){
            throw new PutRequestException409("This game is currently not running!");
        }

        gameById.setStatus(GameStatus.FINISHED);

        //count all the total amount of points gained by all users
        for (Card card : gameById.getCorrectlyGuessed().getCardList()){
            gameById.addScore(card.getScore());
        }

        Game resetGame = resetGameFields(gameById);

        Game savedGame = gameRepository.save(resetGame);
        gameRepository.flush();

        return savedGame;
    }

    //add a user to an existing game
    public Game addUserToGame(Long gameId, Long userId) throws PutRequestException409 {
        Game gameById = getGameById(gameId);

        if (gameById.getUserIds().contains(userId)){
            throw new PutRequestException409("The user has already joined the game!");
        }
        gameById.addUserId(userId);

        Game savedGame = gameRepository.save(gameById);
        gameRepository.flush();

        return savedGame;
    }

    //remove a user from an existing game
    public Game removeUserFromGame(Long gameId, Long userId) {
        Game gameById = getGameById(gameId);
        gameById.removeUserId(userId);

        Game savedGame = gameRepository.save(gameById);
        gameRepository.flush();

        return savedGame;
    }

    public Game resetGameStats(Long gameId){
        Game gameById = getGameById(gameId);
        Game updatedGame = resetGameFields(gameById);

        Game savedGame = gameRepository.save(updatedGame);
        gameRepository.flush();

        return savedGame;
    }

    //fetch game by id from the repository and get the top card from the deck as active card
    public Card getActiveCard(Long id) throws Exception {
        Game gameById = getGameById(id);

        gameById.setActiveCardFromDeck();

        Game savedGame = gameRepository.save(gameById);
        gameRepository.flush();

        return savedGame.getActiveCard();
    }

    //fetch game by id from the repository and set the word of it's active card, which matches the id in cardPutDTo, to true
    public Game setChosenWord(Long id, String chosenWord) throws Exception {
        Game gameById = getGameById(id);

        if (gameById.getActiveCard()== null){
            throw new PutRequestException404("This game contains no active card!");
        }

        if (!gameById.getActiveCard().getMysteryWords().contains(chosenWord)){
            throw new PutRequestException400(String.format("The word '%s' is not on the active card!", chosenWord));
        }

        gameById.setChosenWord(chosenWord);
        gameById.setWordStatus(ChosenWordStatus.SELECTED);
        gameById.setChosenWordCounter(0);
        gameById = gameRepository.save(gameById);
        gameRepository.flush();

        return gameById;
    }

    public Game updateChosenWord(Long id, ChosenWordPutDTO chosenWordPutDTO) throws PutRequestException409 {
        Game gameById = getGameById(id);

        //check if during this turn, a word has already been rejected
        if (!gameById.getChangeWord()){
            throw new PutRequestException409("You can only reject one word per turn!");
        }
        //check if the current word has been rejected by every user
        if (gameById.getUserIds().size() - 1 == gameById.getChosenWordCounter()){
            throw new PutRequestException409("The chosen word has already been checked by every user!");
        }

        gameById.addWordCounter();

        //word is already rejected
        if (gameById.getWordStatus() == ChosenWordStatus.REJECTED){
            //current user is the last user to reject or accept
            if (gameById.getUserIds().size() - 1 == gameById.getChosenWordCounter()){
                gameById.setWordStatus(ChosenWordStatus.REJECTEDBYALL);
                gameById.setChangeWord(false);
            }
            gameById = gameRepository.save(gameById);
            gameRepository.flush();
            return gameById;
        }
        //word get's rejected by current user
        if (!chosenWordPutDTO.getStatus()){
            //current user is the last user to reject
            if (gameById.getUserIds().size() - 1 == gameById.getChosenWordCounter()){
                gameById.setWordStatus(ChosenWordStatus.REJECTEDBYALL);
                gameById.setChangeWord(false);
            }
            else {
                gameById.setWordStatus(ChosenWordStatus.REJECTED);
            }
            gameById = gameRepository.save(gameById);
            gameRepository.flush();
            return gameById;
        }
        //word is accepted by all users
        if (chosenWordPutDTO.getStatus() && gameById.getUserIds().size() - 1 == gameById.getChosenWordCounter()){
            gameById.setWordStatus(ChosenWordStatus.ACCEPTED);
            gameById.setChangeWord(false);
            gameById = gameRepository.save(gameById);
            gameRepository.flush();
            return gameById;
        }
        //word is accepted but not all users have made their decision yet
        gameById = gameRepository.save(gameById);
        gameRepository.flush();
        return gameById;
    }

    //checks a clue with the parser, sets the clue as valid or invalid and adds it to the games clue list
    public Clue addClueToGame(Long id, Clue clueInput) throws PostRequestException409, IOException {
       Game gameById = getGameById(id);

        if(!gameById.getNormalMode()){
            if (gameById.getUserIds().size() - 1 == gameById.getClueCounter()/2){
                String message = "There are already enough clues! Therefore, this clue can't be added!";
                throw new PutRequestException409(message);
            }
        }else
        if (gameById.getUserIds().size() - 1 == gameById.getClues().size()){
            String message = "There are already as many clues as users! Therefore, this clue can't be added!";
            throw new PostRequestException409(message);
        }
        Clue checkedClue = ClueChecker.checkClue(clueInput, gameById);

        gameById.addClue(checkedClue);
        //user ran out off time while giving a clue
        if (checkedClue.getTime() == -1){
            gameById.addScoreToCard(0);
        }
        //reward fast clues by giving more points
        else if (checkedClue.getTime() <= TIME_CLUE){
            gameById.addScoreToCard(MAX_POINTS);
        }
        else {
            gameById.addScoreToCard(MIN_POINTS);
        }

        gameRepository.save(gameById);
        gameRepository.flush();

        return checkedClue;
    }

    public Game setCluesToInvalid(Long id, List<String> cluesToDelete){
        Game gameById = getGameById(id);

        if (gameById.getUserIds().size() - 1 == gameById.getClueCounter()){
            String message = "Everyone checked the clues already!";
            throw new PutRequestException409(message);
        }

        for (String clue : cluesToDelete){
            gameById.setClueToInvalid(clue);
        }

        gameById.addManualClueCounter();
        gameById = gameRepository.save(gameById);
        gameRepository.flush();

        return gameById;
    }

    public Game skipGuessing(Long id) throws Exception {
        Game gameById = getGameById(id);

        //move active card to game box
        gameById.getGameBox().addCard(gameById.getActiveCard());

        Guess guess = new Guess();
        guess.setGuessWord("Skipped");
        guess.setGuessStatus(GuessStatus.WRONG);
        gameById.setGuess(guess);
        gameById.addRound();

        gameById = gameRepository.save(gameById);
        gameRepository.flush();

        return gameById;
    }

    public Guess getGuess(Long id){
        Game gameById = getGameById(id);
        Guess guess = gameById.getGuess();
        if (guess == null){
            guess = new Guess();
            guess.setGame(null);
            guess.setGuessWord(null);
            guess.setGuessStatus(GuessStatus.NOGUESS);
        }
        return guess;
    }

    public Guess makeGuess(Long id, Guess guessInput) throws Exception{
        Game gameById = getGameById(id);

        if (gameById.getGuess() != null){
            throw new PostRequestException409("A guess has already been made this round!");
        }

        //guess is correct
        if(gameById.getChosenWord().equalsIgnoreCase(guessInput.getGuessWord())){
            //user ran out off time while guessing
            if (guessInput.getTime() == -1){
                gameById.addScoreToCard(0);
            }
            //reward fast clues by giving more points
            else if (guessInput.getTime() <= TIME_GUESS){
                gameById.addScoreToCard(MAX_POINTS);
            }
            else {
                gameById.addScoreToCard(MIN_POINTS);
            }

            //move active card to Guessed Pile
            gameById.addToCorrectlyGuessed(gameById.getActiveCard());
            guessInput.setGuessStatus(GuessStatus.CORRECT);
        }
        //guess is wrong
        else{
            guessInput.setGuessStatus(GuessStatus.WRONG);
            gameById.addCardToGameBox(gameById.getActiveCard());
            //not final round
            if (gameById.getDeckSize() > 0){
                gameById.addCardToGameBox(gameById.getTopCardFromDeck());
            }
            //final round
            else {
                gameById.addCardToGameBox(gameById.getTopCardFromCorrectlyGuessed());
            }
        }
        gameById.setGuess(guessInput);
        gameById.addRound();

        gameRepository.save(gameById);
        gameRepository.flush();

        return guessInput;
    }

    private Game resetGameFields(Game gameInput){
        gameInput.setActiveCard(null);

        //delete current chosen word
        gameInput.setChosenWord(null);
        gameInput.setWordStatus(ChosenWordStatus.NOCHOSENWORD);

        //reset word counter
        gameInput.setChosenWordCounter(0);

        //reset clue counter
        gameInput.setManualClueCounter(0);

        //delete all clues
        clueRepository.deleteAll();
        gameInput.setClues(new ArrayList<>());


        //pass the turn to the next user
        int index = getUserIndex(gameInput);
        gameInput.setCurrentUserId(gameInput.getUserIds().get(index));

        //reset this so users can reject a chosen word again
        gameInput.setChangeWord(true);

        guessRepository.deleteAll();
        gameInput.setGuess(null);

        return gameInput;
    }

    //returns the index for the list of userIds, so that the new currentUserId can be set correctly
    public int getUserIndex(Game game) {
        int currentUserIndex = game.getUserIds().indexOf(game.getCurrentUserId());
        if (currentUserIndex + 1 == game.getUserIds().size()) {
            return 0;
        }
        return currentUserIndex + 1;
    }

    private Game createGameElements(Game newGame) throws FileNotFoundException {
        //create Game Box
        GameBox gameBox = new GameBox();
        newGame.setGameBox(gameBox);

        //Create Deck of 13 cards
        Deck deck = new Deck();
        deck.setCardList(createCards());
        newGame.setDeck(deck);

        //Create Deck for the correctly guessed Cards
        newGame.setCorrectlyGuessed(new Deck());

        //Create Clue List
        List<Clue> clues = new ArrayList<>();
        newGame.setClues(clues);

        return newGame;
    }

    //creates 13 random cards, each containing 5 random words
    public List<Card> createCards() throws FileNotFoundException {

        List<String> wordList = new ArrayList<>();
        List<Card> cardList = new ArrayList<>();

        //create the Mystery Words and set their attributes
        try (Scanner s = new Scanner(new FileReader("src/main/resources/JustOneWordsEN.txt"))) {
            while (s.hasNext()) {
                wordList.add(s.nextLine());
            }
        }
        //create the cards
        Collections.shuffle(wordList);
        for (int i = 0; i<13; i++){
            List<String> wordsOnCard = new ArrayList<>();
            for (int j = 0; j<5; j++ ){
                wordsOnCard.add(wordList.remove(0));
            }
            Card newCard = new Card();
            newCard.setMysteryWords(wordsOnCard);
            newCard.setScore(0);
            cardList.add(newCard);
        }
        return cardList;
    }
}
