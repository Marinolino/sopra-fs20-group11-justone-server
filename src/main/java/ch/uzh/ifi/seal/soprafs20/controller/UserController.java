package ch.uzh.ifi.seal.soprafs20.controller;

import ch.uzh.ifi.seal.soprafs20.entity.User;
import ch.uzh.ifi.seal.soprafs20.exceptions.API.GET.GetRequestException400;
import ch.uzh.ifi.seal.soprafs20.exceptions.API.GET.GetRequestException404;
import ch.uzh.ifi.seal.soprafs20.exceptions.API.GET.GetRequestException409;
import ch.uzh.ifi.seal.soprafs20.exceptions.API.POST.PostRequestException409;
import ch.uzh.ifi.seal.soprafs20.exceptions.API.PUT.PutRequestException204;
import ch.uzh.ifi.seal.soprafs20.exceptions.API.PUT.PutRequestException401;
import ch.uzh.ifi.seal.soprafs20.rest.dto.UserGetDTO;
import ch.uzh.ifi.seal.soprafs20.rest.dto.UserPostDTO;
import ch.uzh.ifi.seal.soprafs20.rest.dto.UserPutDTO;
import ch.uzh.ifi.seal.soprafs20.rest.mapper.DTOMapper;
import ch.uzh.ifi.seal.soprafs20.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * User Controller
 * This class is responsible for handling all REST request that are related to the user.
 * The controller will receive the request and delegate the execution to the UserService and finally return the result.
 */
@RestController
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<UserGetDTO> getAllUsers() {
        // fetch all users in the internal representation
        List<User> users = userService.getUsers();
        List<UserGetDTO> userGetDTOs = new ArrayList<>();

        // convert each user to the API representation
        for (User user : users) {
            userGetDTOs.add(DTOMapper.INSTANCE.convertEntityToUserGetDTO(user));
        }
        return userGetDTOs;
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public UserGetDTO createUser(@RequestBody UserPostDTO userPostDTO) throws PostRequestException409 {
        // convert API user to internal representation
        User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);

        // create user
        User createdUser = userService.createUser(userInput);

        // convert internal representation of user back to API
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(createdUser);
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserGetDTO getUserById(@PathVariable("id") Long id) throws GetRequestException404 {
        // get user
        User userById = userService.getUserById(id);

        if (userById == null){
            throw new GetRequestException404("No user was found!", HttpStatus.NOT_FOUND);
        }

        // convert internal representation of user back to API
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(userById);
    }

    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public UserGetDTO updateUserById(@PathVariable("id") Long id, @RequestBody UserPutDTO userPutDTO) throws GetRequestException400, GetRequestException404 {
        // convert API user to internal representation
        User userInput = DTOMapper.INSTANCE.convertUserPutDTOtoEntity(userPutDTO);

        // update user
        User userById = userService.updateUser(id, userInput);

        // convert internal representation of user back to API
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(userById);
    }

    @PutMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserGetDTO logIn(@RequestBody UserPutDTO userPutDTO) throws PutRequestException204, PutRequestException401 {
        User userInput = DTOMapper.INSTANCE.convertUserPutDTOtoEntity(userPutDTO);
        User userOutput = userService.logIn(userInput);
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(userOutput);
    }

    @PutMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public UserGetDTO logOut(@RequestBody UserPutDTO userPutDTO) throws GetRequestException409 {
        User userInput = DTOMapper.INSTANCE.convertUserPutDTOtoEntity(userPutDTO);
        User userOutput = userService.logOut(userInput.getId());
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(userOutput);
    }

    @PutMapping("/users/join/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserGetDTO joinGame(@PathVariable("id") Long id) throws GetRequestException409 {
        User userOutput = userService.joinGame(id);
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(userOutput);
    }

    @PutMapping("/users/leave/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserGetDTO leaveGame(@PathVariable("id") Long id) throws GetRequestException409 {
        User userOutput = userService.leaveGame(id);
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(userOutput);
    }

    @PutMapping("/users/gamestats/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserGetDTO updateUserGameStats(@PathVariable("id") Long id, @RequestBody UserPutDTO userPutDTO) throws PutRequestException204, PutRequestException401 {
        User userInput = DTOMapper.INSTANCE.convertUserPutDTOtoEntity(userPutDTO);
        User userOutput = userService.updateUserGameStats(id, userInput);
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(userOutput);
    }

    @PutMapping("/users/score/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserGetDTO updateUserScore(@PathVariable("id") Long id, @RequestBody UserPutDTO userPutDTO) throws PutRequestException204, PutRequestException401 {
        User userInput = DTOMapper.INSTANCE.convertUserPutDTOtoEntity(userPutDTO);
        User userOutput = userService.updateUserScore(id, userInput);
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(userOutput);
    }
}
