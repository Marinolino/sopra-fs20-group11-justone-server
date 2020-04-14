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
    public UserGetDTO getUserById(@PathVariable("id") Long id) throws GetRequestException400, GetRequestException404 {


       //check if a user id is valid
        if (id == null || id.longValue() == 0) {
            throw new GetRequestException400("Id should not empty!", HttpStatus.BAD_REQUEST);
        }

        // get user
        User userById = userService.getUserById(id);

        //check if a user was found
        if (userById == null) {
            throw new GetRequestException404("No user was found!", HttpStatus.NOT_FOUND);
        }

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
        System.out.println(userOutput.getId());
        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(userOutput);
    }
}
