package ch.uzh.ifi.seal.soprafs20.service;

import ch.uzh.ifi.seal.soprafs20.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs20.entity.User;
import ch.uzh.ifi.seal.soprafs20.exceptions.api.get.GetRequestException409;
import ch.uzh.ifi.seal.soprafs20.exceptions.api.post.PostRequestException409;
import ch.uzh.ifi.seal.soprafs20.exceptions.api.put.PutRequestException204;
import ch.uzh.ifi.seal.soprafs20.exceptions.api.put.PutRequestException401;
import ch.uzh.ifi.seal.soprafs20.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * User Service
 * This class is the "worker" and responsible for all functionality related to the user
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back to the caller.
 */
@Service
@Transactional
public class UserService {

    public static final int DUPLICATECLUE_MULTIPLIER = 1;
    public static final int CORRECTGUESS_MULTIPLIER = 1;

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(@Qualifier("userRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    public User getUserById(Long id) {
        User userById = userRepository.findById(id).orElse(null);

        if (userById == null){
            throw new GetRequestException409("No user found in repository!");
        }

        return userById;
    }

    public User findByUsername(String name){
        return this.userRepository.findByUsername(name);
    }

    public User createUser(User newUser) {

        checkIfInputIsValid(newUser);

        newUser.setToken(UUID.randomUUID().toString());
        newUser.setStatus(UserStatus.OFFLINE);
        newUser.setCorrectlyGuessed(0);
        newUser.setDuplicateClues(0);
        newUser.setScore(0);
        newUser.setGamesPlayed(0);

        checkIfUserExists(newUser);

        // saves the given entity but data is only persisted in the database once flush() is called
        newUser = userRepository.save(newUser);
        userRepository.flush();

        return newUser;
    }

    //checks if the user exists and if the password is correct
    public User logIn(User userToCheck) {
        User userByUsername = findByUsername(userToCheck.getUsername());

        if(userByUsername == null){
            String message = String.format("There is no registered user '%s'! Please try again!", userToCheck.getUsername());
            throw new PutRequestException401(message);
        }
        if(!userByUsername.getPassword().equals(userToCheck.getPassword())){
            String message = "The password is incorrect! Please try again!";
            throw new PutRequestException401(message);
        }
        if(userByUsername.getStatus() == UserStatus.ONLINE){
            String message = String.format("The user '%s' is already logged in!", userToCheck.getUsername());
            throw new PutRequestException204(message);
        }
        userByUsername.setToken(UUID.randomUUID().toString());
        userByUsername.setStatus(UserStatus.ONLINE);
        userByUsername = userRepository.save(userByUsername);
        userRepository.flush();
        return (userByUsername);
    }

    public User logOut(Long id) {
        User user = getUserById(id);
        user.setStatus(UserStatus.OFFLINE);
        user = userRepository.save(user);
        userRepository.flush();
        return user;
    }

    public User updateUser(Long id, User user) {

        checkIfInputIsValid(user);
        checkIfUserExists(user);
        User userById = getUserById(id);

        if (user.getUsername() != null){
            userById.setUsername(user.getUsername());
        }
        if (user.getName() != null){
            userById.setName(user.getName());
        }

        User updatedUser = userRepository.save(userById);
        userRepository.flush();

        return updatedUser;
    }

    public User joinGame(Long id){
        User userById = getUserById(id);

        userById.setStatus(UserStatus.INGAME);
        User updatedUser = userRepository.save(userById);
        userRepository.flush();

        return updatedUser;
    }

    public User leaveGame(Long id){
        User userById = getUserById(id);

        userById.setStatus(UserStatus.ONLINE);
        userById.setCorrectlyGuessed(0);
        userById.setDuplicateClues(0);
        User updatedUser = userRepository.save(userById);
        userRepository.flush();

        return updatedUser;
    }


    //update the amount of correctly guessed words or the duplicate clues
    public User updateUserGameStats(Long id, User userInput){
        User userById = getUserById(id);
        int guess = userInput.getCorrectlyGuessed();
        int clue = userInput.getDuplicateClues();

        if (guess > 0){
            userById.addCorrectlyGuessed(guess);
        }
        if (clue > 0){
            userById.addDuplicateClues(clue);
        }

        User updatedUser = userRepository.save(userById);
        userRepository.flush();

        return updatedUser;
    }

    //update the score of the user, depending on correctly guessed words and duplicate clues
    public User updateUserScore(Long id, User userInput){
        User userById = getUserById(id);
        int score = userInput.getScore();

        //increase gamesPlayed by 1
        userById.addGames();
        //User receives penalty or bonus for correctly guessed words and duplicate clues
        int updatedScore = score - userById.getDuplicateClues() * DUPLICATECLUE_MULTIPLIER + userById.getCorrectlyGuessed() * CORRECTGUESS_MULTIPLIER;
        userById.addScore(updatedScore);
        userById.setCorrectlyGuessed(0);
        userById.setDuplicateClues(0);

        User updatedUser = userRepository.save(userById);
        userRepository.flush();

        return updatedUser;
    }


    /**
     * This is a helper method that will check the uniqueness criteria of the username defined in the User entity.
     * The method will do nothing if the input is unique and throw an error otherwise.
     *
     * @param userToBeCreated
     * @throws PostRequestException409
     * @see User
     */
    private void checkIfUserExists(User userToBeCreated) {

        if (userToBeCreated.getId() == null) {
            User userByUsername = userRepository.findByUsername(userToBeCreated.getUsername());
            if (userByUsername != null) {
                String message = String.format("There is already a user '%s'! Please try again!", userByUsername.getUsername());
                throw new PostRequestException409(message);
            }
        } else {
            User userByUsername = userRepository.findByUsernameAndIdNot(userToBeCreated.getUsername(), userToBeCreated.getId());
            if (userByUsername != null) {
                String message = String.format("There is already a user '%s'! Please try again!", userByUsername.getUsername());
                throw new PostRequestException409(message);
            }
        }
    }

    /**
     * This is a helper method that will check the validness criteria of the username and the password defined in the User entity.
     * The method will do nothing if the input is valid and throw an error otherwise.
     *
     * @param userToBeCreated
     * @throws PostRequestException409
     * @see User
     */
    public void checkIfInputIsValid(User userToBeCreated) {
        String name = userToBeCreated.getName();
        if (name == null || name.length() > 20) {
            String message = "The field 'NAME' must contain between 1 and 20 characters! Therefore, the user could not be created!";
            throw new PostRequestException409(message);
        } else if (userToBeCreated.getName().contains(" ")) {
            String message = "The field 'NAME' must not contain any whitespaces! Therefore, the user could not be created!";
            throw new PostRequestException409(message);
        }

        String username = userToBeCreated.getUsername();
        if (username == null || username.length() > 20) {
            String message = "The field 'USERNAME' must contain between 1 and 20 characters! Therefore, the user could not be created!";
            throw new PostRequestException409(message);
        } else if (username.contains(" ")) {
            String message = "The field 'USERNAME' must not contain any whitespaces! Therefore, the user could not be created!";
            throw new PostRequestException409(message);
        }

        String password = userToBeCreated.getPassword();
        if (password == null || password.length() > 20) {
            String message = "The field 'PASSWORD' must contain between 1 and 20 characters! Therefore, the user could not be created!";
            throw new PostRequestException409(message);
        } else if (password.contains(" ")) {
            String message = "The field 'PASSWORD' must not contain any whitespaces! Therefore, the user could not be created!";
            throw new PostRequestException409(message);
        }
    }
}
