package ch.uzh.ifi.seal.soprafs20.service;

import ch.uzh.ifi.seal.soprafs20.ClueChecker.ClueChecker;
import ch.uzh.ifi.seal.soprafs20.constant.ChosenWordStatus;
import ch.uzh.ifi.seal.soprafs20.entity.Game.*;
import ch.uzh.ifi.seal.soprafs20.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs20.exceptions.API.GET.GetRequestException404;
import ch.uzh.ifi.seal.soprafs20.exceptions.API.POST.PostRequestException409;
import ch.uzh.ifi.seal.soprafs20.exceptions.API.PUT.PutRequestException400;
import ch.uzh.ifi.seal.soprafs20.exceptions.API.PUT.PutRequestException401;
import ch.uzh.ifi.seal.soprafs20.exceptions.API.PUT.PutRequestException403;
import ch.uzh.ifi.seal.soprafs20.exceptions.API.PUT.PutRequestException404;
import ch.uzh.ifi.seal.soprafs20.repository.*;
import ch.uzh.ifi.seal.soprafs20.rest.dto.ChosenWordPutDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

@Service
@Transactional
public class GameService {

    private final GameRepository gameRepository;

    private final Logger log = LoggerFactory.getLogger(GameService.class);

    public GameService(@Qualifier("gameRepository") GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> getGames() {
        return this.gameRepository.findAll();
    }

    public Game getGameById(Long id){
        Game gameById = gameRepository.findById(id).orElse(null);
        if (gameById == null){
            throw new GetRequestException404("No game was found!", HttpStatus.NOT_FOUND);
        }
        return gameById;
    }

    public Game createGame(Game newGame) throws FileNotFoundException {
        Game createdGame = createGameElements(newGame);
        createdGame.setToken(UUID.randomUUID().toString());
        createdGame.setStatus(GameStatus.CREATED);
        createdGame.setChangeWord(true);
        createdGame.setScore(0);
        createdGame.setRound(0);
        createdGame.setWordStatus(ChosenWordStatus.NONE);
        createdGame.addUserId(createdGame.getCurrentUserId());

        // saves the given entity but data is only persisted in the database once flush() is called
        Game savedGame = gameRepository.save(createdGame);
        gameRepository.flush();

        log.debug("Created Information for Game: {}", newGame);
        return savedGame;
    }

    public Game startGame(Long id){
        Game gameById = getGameById(id);
        gameById.setStatus(GameStatus.RUNNING);

        //check and set if it is a game with 3 players or more than 3 players
        if (gameById.getUserIds().size() == 3){
            gameById.setNormalMode(false);
        }
        else {
            gameById.setNormalMode(true);
        }

        // saves the given entity but data is only persisted in the database once flush() is called
        Game savedGame = gameRepository.save(gameById);
        gameRepository.flush();

        return savedGame;
    }

    public Game finishGame(Long id){
        Game gameById = getGameById(id);
        gameById.setStatus(GameStatus.FINISHED);

        //count all the total amount of points gained by all users
        for (Card card : gameById.getCorrectlyGuessed().getCardList()){
            gameById.addScore(card.getScore());
        }

        Game savedGame = gameRepository.save(gameById);
        gameRepository.flush();

        return savedGame;
    }

    public Game createGameElements(Game newGame) throws FileNotFoundException {
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

    //add a user to an existing game
    public Game addUserToGame(Long id, Game game) throws Exception {
        Game gameById = getGameById(id);
        gameById.addUserId(game.getCurrentUserId());

        Game savedGame = gameRepository.save(gameById);
        gameRepository.flush();

        return savedGame;
    }

    //remove a user from an existing game
    public Game removeUserFromGame(Long id, Game game) throws Exception {
        Game gameById = getGameById(id);
        gameById.removeUserId(game.getCurrentUserId());

        Game savedGame = gameRepository.save(gameById);
        gameRepository.flush();

        return savedGame;
    }

    //fetch game by id from the repository and get the top card from the deck as active card
    public Card getActiveCard(Long id) throws Exception {
        Game gameById = getGameById(id);
        gameById.setActiveCardFromDeck();
        gameById.addRound();

        Game savedGame = gameRepository.save(gameById);
        gameRepository.flush();

        return savedGame.getActiveCard();
    }

    //fetch game by id from the repository and set the word of it's active card, which matches the id in cardPutDTo, to true
    public Game setChosenWord(Long id, String chosenWord) throws Exception {
        Game gameById = getGameById(id);

        if (gameById.getActiveCard()== null){
            throw new PutRequestException404("This game contains no active card!", HttpStatus.NOT_FOUND);
        }

        if (!gameById.getActiveCard().getMysteryWords().contains(chosenWord)){
            throw new PutRequestException400(String.format("The word '%s' is not on the active card!", chosenWord), HttpStatus.BAD_REQUEST);
        }

        gameById.setChosenWord(chosenWord);
        gameById.setWordStatus(ChosenWordStatus.SELECTED);
        gameById = gameRepository.save(gameById);
        gameRepository.flush();

        return gameById;
    }

    public Game updateChosenWord(Long id, ChosenWordPutDTO chosenWordPutDTO) throws Exception {
        Game gameById = getGameById(id);

        //check if during this turn, a word has already been rejected
        if (!gameById.getChangeWord()){
            throw new PutRequestException403("You can only reject one word per turn!", HttpStatus.FORBIDDEN);
        }

        gameById.addWordCounter();

        //word is already rejected
        if (gameById.getWordStatus() == ChosenWordStatus.REJECTED){
            gameById = gameRepository.save(gameById);
            gameRepository.flush();
            return gameById;
        }
        //word get's rejected
        if (!chosenWordPutDTO.getStatus()){
            gameById.setWordStatus(ChosenWordStatus.REJECTED);
            gameById.setChangeWord(false);
            gameById = gameRepository.save(gameById);
            gameRepository.flush();
            return gameById;
        }
        //word is accepted by all users
        if (chosenWordPutDTO.getStatus() && gameById.getUserIds().size() == gameById.getWordCounter()){
            gameById.setWordStatus(ChosenWordStatus.ACCEPTED);
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
    public Clue addClueToGame(Long id, Clue clueInput) throws Exception {
       Game gameById = getGameById(id);

        if (gameById.getUserIds().size() == gameById.getClues().size()){
            String message = "There are already as many clues as users! Therefore, this clue can't be added!";
            throw new PostRequestException409(message, HttpStatus.CONFLICT);
        }
        Clue checkedClue = ClueChecker.checkClue(clueInput, gameById);

        gameById.addClue(checkedClue);
        gameById = gameRepository.save(gameById);
        gameRepository.flush();

        return checkedClue;
    }

    public Game setCluesToInvalid(Long id, List<String> cluesToDelete){
        Game gameById = getGameById(id);

        for (String clue : cluesToDelete){
            gameById.setClueToInvalid(clue);
            gameById = gameRepository.save(gameById);
            gameRepository.flush();
        }
        return gameById;
    }

    //resets clues, moves active card to gameBox, changes active user
    public Game skipGuessing(Long id){
        Game gameById = getGameById(id);

        //move active card to game box
        gameById.getGameBox().addCard(gameById.getActiveCard());
        gameById.setActiveCard(null);

        //delete current chosen word
        gameById.setChosenWord(null);
        gameById.setWordStatus(ChosenWordStatus.NONE);

        //reset word counter
        gameById.setWordCounter(0);

        //delete all clues
        gameById.setClues(null);

        //pass the turn to the next user
        int index = getUserIndex(gameById);
        gameById.setCurrentUserId(gameById.getUserIds().get(index));

        //reset this so users can reject a chosen word again
        gameById.setChangeWord(true);

        gameById = gameRepository.save(gameById);
        gameRepository.flush();

        return gameById;
    }

    //returns the index for the list of userIds, so that the new currentUserId can be set correctly
    public int getUserIndex(Game game) {
        int currentUserIndex = game.getUserIds().indexOf(game.getCurrentUserId());
        if (currentUserIndex + 1 == game.getUserIds().size()) {
            return 0;
        }
        return currentUserIndex + 1;
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
