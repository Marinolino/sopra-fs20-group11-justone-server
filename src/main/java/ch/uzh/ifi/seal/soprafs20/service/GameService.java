package ch.uzh.ifi.seal.soprafs20.service;

import ch.uzh.ifi.seal.soprafs20.ClueChecker.ClueChecker;
import ch.uzh.ifi.seal.soprafs20.entity.Game.*;
import ch.uzh.ifi.seal.soprafs20.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs20.exceptions.API.GET.GetRequestException404;
import ch.uzh.ifi.seal.soprafs20.repository.*;
import ch.uzh.ifi.seal.soprafs20.rest.dto.CardPutDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public GameService(GameRepository gameRepository) {
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
        createdGame.setScore(0);
        createdGame.setRound(0);
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

    public Game addUserToGame(Long id, Game game) throws Exception {
        Game gameById = getGameById(id);
        gameById.addUserId(game.getCurrentUserId());

        Game savedGame = gameRepository.save(gameById);
        gameRepository.flush();

        return savedGame;
    }

    //fetch game by id from the repository and get the top card from the deck as active card
    public Card getActiveCard(Long id) throws Exception {
        Game gameById = getGameById(id);
        gameById.setActiveCardFromDeck();
        gameById.setRound(gameById.getRound() + 1);

        Game savedGame = gameRepository.save(gameById);
        gameRepository.flush();

        return savedGame.getActiveCard();
    }

    //fetch game by id from the repository and set the word of it's active card, which matches the id in cardPutDTo, to true
    public void setChosenWord(Long id, CardPutDTO cardPutDTO) throws Exception {
        Game gameById = getGameById(id);

        Card card = gameById.getActiveCard();
        if (card != null) {
            card.setChosenWord(cardPutDTO.getId());
        }

        gameById.setActiveCard(card);
        gameRepository.save(gameById);
        gameRepository.flush();
    }

    //checks a clue with the parser, if the clue is valid, it is added to the games clue list
    public Game addClueToGame(Long id, String clueInput){
        Game gameById = getGameById(id);
        //check if the clue is valid
        if (ClueChecker.checkClue(clueInput)){
            Clue validClue = new Clue();
            validClue.setClue(clueInput);
            gameById.addClue(validClue);

            gameById = gameRepository.save(gameById);
            gameRepository.flush();
        }
        return gameById;
    }

    public Game deleteClues(Long id, List<String> cluesToDelete){
        Game gameById = getGameById(id);

        for (String clue : cluesToDelete){
            gameById.deleteClue(clue);
            gameById = gameRepository.save(gameById);
            gameRepository.flush();
        }
        return gameById;
    }

    //creates 13 random cards, each containing 5 random words
    public List<Card> createCards() throws FileNotFoundException {

        List<MysteryWord> wordList = new ArrayList<>();
        List<Card> cardList = new ArrayList<>();

        //create the Mystery Words and set their attributes
        try (Scanner s = new Scanner(new FileReader("src/main/resources/JustOneWordsEN.txt"))) {
            while (s.hasNext()) {
                MysteryWord mysteryWord = new MysteryWord();
                mysteryWord.setChosen(false);
                mysteryWord.setWord(s.nextLine());
                wordList.add(mysteryWord);
            }
        }
        //create the cards
        Collections.shuffle(wordList);
        for (int i = 0; i<13; i++){
            List<MysteryWord> wordsOnCard = new ArrayList<>();
            for (int j = 0; j<5; j++ ){
                wordsOnCard.add(wordList.remove(0));
            }
            Card newCard = new Card();
            newCard.setWordList(wordsOnCard);
            cardList.add(newCard);
        }
        return cardList;
    }
}
