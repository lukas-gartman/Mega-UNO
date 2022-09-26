package org.megauno.app.model.Player;

import org.megauno.app.model.Cards.ICard;

import java.util.ArrayList;
import java.util.List;

//
public class Player {

    //Bool for if the player has said uno this round
    private boolean saidUno = false;

    private List<ICard> selectedCards = new ArrayList<>();
    //The hand of the player, has the cards
    private List<ICard> hand = new ArrayList<>();
    //The selected card
    Player(ArrayList<ICard> hand) {
        this.hand = hand;
    }
    Player() {
        this.hand = new ArrayList<>();
    }

    public void selectCard(ICard c){
        if (hand.contains(c)) {
            selectedCards.add(c);
        }
    }
    public void unSelectCard(ICard c){
        selectedCards.remove(c);
    }
    public void discardAllSelectedCards(){
        selectedCards = new ArrayList<>();
    }


    public void sayUno(){
        saidUno = true;
    }

    public boolean uno() {
        return saidUno;
    }

    public int numOfCards(){
        return hand.size();
    }

    public List<ICard> getCards(){
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
    public List<ICard> play(ICard topCard){
        return copyCards(selectedCards);
    }

    private List<ICard> copyCards(List<ICard> cards){
        List<ICard> copy = new ArrayList<>();
        for (ICard card: cards) {
            copy.add(card.copyCard());
        }
        return copy;
    }


}
