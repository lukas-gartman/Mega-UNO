package org.megauno.app.model.Player;

import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Game.IPlayer;

import java.util.ArrayList;
import java.util.List;

//
public class Player {
	private int id;
    //Bool for if the player has said uno this round
    private boolean saidUno = false;
    private List<ICard> selectedCards = new ArrayList<>();
    //The hand of the player, has the cards
    private List<ICard> hand;
    //The selected card

    public Player(int id) {
        this.hand = new ArrayList<>();
        this.id = id;
    }

    public Player(int id, List<ICard> hand) {
        this.hand = hand;
        this.id = id;
    }

    public int getId(){
        return id;
    }

    // Bug, since the same card can be picked several times
    public void selectCard(ICard c){
        // This for now, but this doesn't distinguish cards by id, so it isn't complete
        if (hand.contains(c) && !selectedCards.contains(c)) {
            selectedCards.add(c);
        }
    }

    public List<ICard> getSelectedCards(){
        return copyCards(selectedCards);
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

    public void unsayUno(){
        saidUno = false;
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


    public void addCards(List<ICard> cards) {
        for (ICard card: cards) {
            this.addCard(card);
        }
    }
    private void removeSelectedCardsFromHand() {
        for (ICard c: selectedCards) {
            hand.remove(c);
        }
    }



    //To get the card a player wants to play
    public List<ICard> play(){
        //saidUno = false; don't understand why this is desired behaviour
        removeSelectedCardsFromHand();
        List<ICard> out = copyCards(selectedCards);
        discardAllSelectedCards();
        return out;
    }

    private List<ICard> copyCards(List<ICard> cards) {
        List<ICard> copy = new ArrayList<>();
        for (ICard card: cards) {
            copy.add(card.copyCard());
        }
        return copy;
    }


}
