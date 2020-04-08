package ch.uzh.ifi.seal.soprafs20.rest.dto;

import ch.uzh.ifi.seal.soprafs20.Game.Card;
import ch.uzh.ifi.seal.soprafs20.Game.Clue;
import ch.uzh.ifi.seal.soprafs20.Game.Deck;
import ch.uzh.ifi.seal.soprafs20.Game.GameBox;
import ch.uzh.ifi.seal.soprafs20.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs20.entity.User;

import java.util.ArrayList;

public class GameGetDTO {
    private Long id;
    private String token;
    private GameStatus status;
    private int score;
    private GameBox gameBox;
    private Deck deck;
    private Deck correctlyGuessed;
    private ArrayList<User> users;
    private ArrayList<Clue> clues;
    private Card activeCard;

}
