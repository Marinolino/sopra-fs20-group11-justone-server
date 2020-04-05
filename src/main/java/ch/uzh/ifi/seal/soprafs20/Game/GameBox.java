package ch.uzh.ifi.seal.soprafs20.Game;

import java.util.ArrayList;

public class GameBox {

    private ArrayList<Card> cardList = new ArrayList<Card>();

    public void addCard(Card card){
        cardList.add(card);
    }


    //Looks if any card's id in the box matches the given id. If a match is found, that card is removed from the box.
    public void removeCard(int Id){
        for (Card card : cardList) {
            if (card.getId() == Id){
                cardList.remove(card);
                break;
            }
        }
    }

    public ArrayList<Card> getCards(){
        return this.cardList;
    }
}