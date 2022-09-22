package org.megauno.app.model.Player;

import org.megauno.app.model.Cards.ICard;

import java.util.ArrayList;
import java.util.List;

//
public class Player {

    //Bool for if the player has said uno this round
    private boolean saidUno = false;
    //The entity who makes the decisions
    private DecisionMaker decisionMaker;
    //The hand of the player, has the cards
    private ArrayList<ICard> hand;

    Player(ArrayList<ICard> hand, DecisionMaker decisionMaker) {
        this.hand = hand;
        this.decisionMaker = decisionMaker;
    }

    boolean uno() {
        return saidUno;
    }


    public ArrayList<ICard> getCards(){
        return copyCards(hand);
    }

    public int numOfCards(){
        return hand.size();
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
        saidUno = decisionMaker.saidUno();
        boolean playable = false;
        ICard selectedCard;
        selectedCard = decisionMaker.chooseCard();
        playable = selectedCard.canBePlayed(topCard);
        while(!playable){
            selectedCard = decisionMaker.chooseCard();
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
