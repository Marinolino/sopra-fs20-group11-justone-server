package ch.uzh.ifi.seal.soprafs20.Game;

import ch.uzh.ifi.seal.soprafs20.exceptions.SopraServiceException;

import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> cardList = new ArrayList<Card>();
    private boolean hasNext;

    public Deck(){
        hasNext = false;
    }

    public void addCard(Card card){
        cardList.add(0, card);
        if (!hasNext)
        hasNext = true;
    }

    public Card getTopCard(){
        if (hasNext){
            return next();
        }
        else{
            String message = "The deck is empty!";
            throw new SopraServiceException(message);
        }
    }

    public Card next(){
        Card cardToReturn = cardList.remove(0);
        if (cardList.size() == 0){
            hasNext = false;
        }
        return cardToReturn;
    }

    public int deckSize(){
        return cardList.size();
    }
}
