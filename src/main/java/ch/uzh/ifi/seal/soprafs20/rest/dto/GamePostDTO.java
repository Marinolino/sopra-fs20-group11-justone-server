package ch.uzh.ifi.seal.soprafs20.rest.dto;

import ch.uzh.ifi.seal.soprafs20.Game.Card;
import ch.uzh.ifi.seal.soprafs20.Game.Clue;
import ch.uzh.ifi.seal.soprafs20.Game.Deck;
import ch.uzh.ifi.seal.soprafs20.Game.GameBox;
import ch.uzh.ifi.seal.soprafs20.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs20.entity.User;
import java.util.ArrayList;

public class GamePostDTO {

    private Long id;

    private ArrayList<User> users;

    private String token;

    private boolean normalMode;

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

    public boolean getNormalMode() {
        return normalMode;
    }

    public void setNormalMode(boolean mode) {
        this.normalMode = mode;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
}
