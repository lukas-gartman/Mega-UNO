package org.megauno.app.model.Player;

import org.megauno.app.model.Cards.ICard;

import java.util.ArrayList;
import java.util.List;

//
public class Player {

    //Bool for if the player has said uno this round
    private boolean saidUno = false;

    private ICard selectedCard;
    //The hand of the player, has the cards
    private ArrayList<ICard> hand;
    //The selected card
    Player(ArrayList<ICard> hand) {
        this.hand = hand;
    }

    public void selectCard(ICard c){
        selectedCard = c;
    }

    boolean uno() {
        return saidUno;
    }


    ArrayList<ICard> getCards(){
        return copyCards(hand);
    }
    public void addCard(ICard card){
        hand.add(card.copyCard());
    }
    public void addCards(List<ICard> cards){
        for (ICard card: cards) {
            addCard(card);
        }
    }



    //To get the card a player wants to play
    public ICard play(ICard topCard){
        boolean playable = selectedCard.canBePlayed(topCard);
        while(!playable){
            playable = selectedCard.canBePlayed(topCard);
            if(playable){
                playable = hand.remove(selectedCard);
            }
        }
        return selectedCard;
    }
    private ArrayList<ICard> copyCards(ArrayList<ICard> cards){
        ArrayList<ICard> copy = new ArrayList<>();
        for (ICard card: cards) {
            copy.add(card.copyCard());
        }
        return copy;
    }


}
