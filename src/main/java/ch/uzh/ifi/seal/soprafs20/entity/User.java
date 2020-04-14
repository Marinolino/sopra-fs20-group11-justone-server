package ch.uzh.ifi.seal.soprafs20.entity;

import ch.uzh.ifi.seal.soprafs20.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs20.entity.Game.Game;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Internal User Representation
 * This class composes the internal representation of the user and defines how the user is stored in the database.
 * Every variable will be mapped into a database field with the @Column annotation
 * - nullable = false -> this cannot be left empty
 * - unique = true -> this value must be unique across the database -> composes the primary key
 */
@Entity
@Table(name = "USER")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(unique = true)
    private String newUsername;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private UserStatus status;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private int gamesPlayed;

    @Column(nullable = false)
    private boolean inGame;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNewUsername() {
        return newUsername;
    }

    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public int getScore(){
        return this.score;
    }

    public void setScore(int score){
        this.score = score;
    }

    public void addScore(int score){
        this.score += score;
    }

    public int getGamesPlayed(){
        return this.gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed){
        this.gamesPlayed = gamesPlayed;
    }

    public void addGames(){
        this.gamesPlayed += 1;
    }

    public boolean getInGame(){
        return this.inGame;
    }

    public void setInGame(boolean inGame){
        this.inGame = inGame;
    }
}
