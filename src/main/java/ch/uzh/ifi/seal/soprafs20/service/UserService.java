package ch.uzh.ifi.seal.soprafs20.service;

import ch.uzh.ifi.seal.soprafs20.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs20.entity.User;
import ch.uzh.ifi.seal.soprafs20.exceptions.API.GET.GetRequestException404;
import ch.uzh.ifi.seal.soprafs20.exceptions.API.GET.GetRequestException409;
import ch.uzh.ifi.seal.soprafs20.exceptions.API.POST.PostRequestException409;
import ch.uzh.ifi.seal.soprafs20.exceptions.API.PUT.PutRequestException204;
import ch.uzh.ifi.seal.soprafs20.exceptions.API.PUT.PutRequestException401;
import ch.uzh.ifi.seal.soprafs20.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
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

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(@Qualifier("userRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    public User getUserById(Long id) throws GetRequestException404 {
        return userRepository.findById(id).orElseThrow(() -> new GetRequestException404("No user was found!", HttpStatus.NOT_FOUND));
    }

    public User findByUsername(String name){
        return this.userRepository.findByUsername(name);
    }

    public User createUser(User newUser) {

        checkIfInputIsValid(newUser);

        newUser.setToken(UUID.randomUUID().toString());
        newUser.setStatus(UserStatus.OFFLINE);
        newUser.setInGame(false);
        newUser.setScore(0);
        newUser.setGamesPlayed(0);

        checkIfUserExists(newUser);

        // saves the given entity but data is only persisted in the database once flush() is called
        newUser = userRepository.save(newUser);
        userRepository.flush();

        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    //checks if the user exists and if the password is correct
    public User logIn(User userToCheck) throws  PutRequestException204, PutRequestException401 {
        User userByUsername = findByUsername(userToCheck.getUsername());

        if(userByUsername == null){
            String message = String.format("There is no registered user '%s'! Please try again!", userToCheck.getUsername());
            throw new PutRequestException401(message, HttpStatus.UNAUTHORIZED);
        }
        if(!userByUsername.getPassword().equals(userToCheck.getPassword())){
            String message = "The password is incorrect! Please try again!";
            throw new PutRequestException401(message, HttpStatus.UNAUTHORIZED);
        }
        if(userByUsername.getStatus() == UserStatus.ONLINE){
            String message = String.format("The user '%s' is already logged in!", userToCheck.getUsername());
            throw new PutRequestException204(message, HttpStatus.NO_CONTENT);
        }
        userByUsername.setToken(UUID.randomUUID().toString());
        userByUsername.setStatus(UserStatus.ONLINE);
        userByUsername = userRepository.save(userByUsername);
        userRepository.flush();
        return (userByUsername);
    }

    public User logOut(Long id) throws GetRequestException409 {
        User user = getUserById(id);

        if (user == null){
            throw new GetRequestException409("No user found in repository!", HttpStatus.NOT_FOUND);
        }

        user.setStatus(UserStatus.OFFLINE);
        user = userRepository.save(user);
        userRepository.flush();
        return user;
    }

    public User updateUser(Long id, User user) throws GetRequestException404, PostRequestException409 {

        checkIfInputIsValid(user);

        checkIfUserExists(user);

        User existedUser = getUserById(id);

        if (user.getUsername() != null){
            existedUser.setUsername(user.getUsername());
        }

        if (user.getName() != null){
            existedUser.setName(user.getName());
        }

        // saves the given entity but data is only persisted in the database once flush() is called
        User updatedUser = userRepository.save(existedUser);
        userRepository.flush();

        log.debug("Updated Information for User: {}", user);
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
                throw new PostRequestException409(message, HttpStatus.CONFLICT);
            }
        } else {
            User userByUsername = userRepository.findByUsernameAndIdNot(userToBeCreated.getUsername(), userToBeCreated.getId());
            if (userByUsername != null) {
                String message = String.format("There is already a user '%s'! Please try again!", userByUsername.getUsername());
                throw new PostRequestException409(message, HttpStatus.CONFLICT);
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

        if (userToBeCreated.getName() != null && userToBeCreated.getName().contains(" ")) {
            String message = "The field 'NAME' must not contain any whitespaces! Therefore, the user could not be created!";
            throw new PostRequestException409(message, HttpStatus.CONFLICT);
        }
        if (userToBeCreated.getUsername() != null && userToBeCreated.getUsername().contains(" ")) {
            String message = "The field 'USERNAME' must not contain any whitespaces! Therefore, the user could not be created!";
            throw new PostRequestException409(message, HttpStatus.CONFLICT);
        }
        if (userToBeCreated.getPassword() != null && userToBeCreated.getPassword().contains(" ")) {
            String message = "The field 'PASSWORD' must not contain any whitespaces! Therefore, the user could not be created!";
            throw new PostRequestException409(message, HttpStatus.CONFLICT);
        }
    }
}
