package org.megauno.app.model.player;

import org.megauno.app.model.cards.ICard;
import org.megauno.app.utility.Publisher.IPublisher;
import org.megauno.app.utility.Publisher.normal.Publisher;
import org.megauno.app.utility.Tuple;

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
    //Publisher for when cards are added to a player
    private Publisher<Tuple<Player, List<ICard>>> onCardsAddedByPlayer = new Publisher<>();

    //Publisher for when cards are added to a player
    private Publisher<Tuple<Player, List<ICard>>> onCardsRemovedByPlayer = new Publisher<>();

    public Player() {
        this.hand = new ArrayList<>();
    }


    /**
     * Creates a player
     * @param hand the cards the player should start with
     */
    public Player(List<ICard> hand) {
        onCardsAddedByPlayer.publish(new Tuple<Player, List<ICard>>(this, hand));
        this.hand = hand;
    }

    /**
     * Selects a card to be played
     * @param c the card to be selected for play
     */
    public void selectCard(ICard c){
        if (hand.contains(c)) {
            for (ICard sc : selectedCards){ if (c == sc) return;}
            selectedCards.add(c);
        }
    }

    /**
     * Returns the currently selected cards
     * @return the currently selected cards
     */
    public List<ICard> getSelectedCards(){
        return (selectedCards);
    }

    /**
     * Unselects a card for play
     * @param c the card to be unselected for play
     */
    public void unSelectCard(ICard c){
        selectedCards.remove(c);
    }


    /**
     * Discard all selected cards so that none are currently for play
     */
    public void discardAllSelectedCards(){
        selectedCards = new ArrayList<>();
    }


    /**
     * Signaling that the player has said uno
     */
    public void sayUno(){
        saidUno = true;
    }

    /**
     * Takes back that the player has said uno
     */
    public void unsayUno(){
        saidUno = false;
    }

    public boolean uno() {
        return saidUno;
    }

    public int numOfCards() {
        return hand.size();
    }


    public List<ICard> getCards(){
        return hand;
    }

    /**
     * Adds a card to the player
     * @param card the card to be added to the player
     */
    public void addCard(ICard card){
        ArrayList<ICard> l = new ArrayList<>();
        l.add(card);
        onCardsAddedByPlayer.publish(new Tuple<Player, List<ICard>>(this, l));
        hand.add(card);
    }

    /**
     * Adds cards to the player
     * @param cards the cards to be added to the player
     */
    public void addCards(List<ICard> cards) {
        for (ICard card : cards) {
            this.addCard(card);
        }
    }

    /**
     * Removes the selected cards from the players cards
     */
    public void removeSelectedCardsFromHand() {
        for (ICard c : selectedCards) {
            hand.remove(c);
        }
        onCardsRemovedByPlayer.publish(new Tuple<Player, List<ICard>>(this, selectedCards));
    }


    /**
     * Simulates the player making their play
     * @return the cards which the player wants to play
     */
    public List<ICard> play() {
        return selectedCards;
    }


    public IPublisher<Tuple<Player, List<ICard>>> getOnCardsAddedByPlayer() {
        return onCardsAddedByPlayer;
    }

    public IPublisher<Tuple<Player, List<ICard>>> getOnCardRemovedByPlayer() {
        return onCardsRemovedByPlayer;
    }
}
