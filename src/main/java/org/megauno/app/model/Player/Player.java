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
    private List<ICard> hand;
    //The selected card

    public Player() {
        this.hand = new ArrayList<>();
    }

    public Player(List<ICard> hand) {
        this.hand = hand;
    }

    public Player(List<ICard> hand) {
        this.hand = hand;
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
    private void removeSelectedCardsFromHand(){
        for (ICard c: selectedCards) {
            hand.remove(c);
        }
    }



    //To get the card a player wants to play
    public List<ICard> play(){
        saidUno = false;
        removeSelectedCardsFromHand();
        List<ICard> out = copyCards(selectedCards);
        discardAllSelectedCards();
        return out;
    }

    private List<ICard> copyCards(List<ICard> cards){
        List<ICard> copy = new ArrayList<>();
        for (ICard card: cards) {
            copy.add(card.copyCard());
        }
        return copy;
    }


}