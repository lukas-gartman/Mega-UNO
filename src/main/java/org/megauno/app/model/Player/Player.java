package org.megauno.app.model.Player;

import org.megauno.app.model.Cards.ICard;
import org.megauno.app.utility.Publisher.IPublisher;
import org.megauno.app.utility.Publisher.condition.ConPublisher;
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

    public Player(List<ICard> hand) {
        this.hand = hand;
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
        ArrayList<ICard> l = new ArrayList<>();
        l.add(card);
        onCardsAddedByPlayer.publish(new Tuple<Player,List<ICard>>(this,l));
        hand.add(card.copyCard());
    }


    public void addCards(List<ICard> cards) {
        for (ICard card : cards) {
            this.addCard(card);
        }
    }
    private void removeSelectedCardsFromHand() {
        for (ICard c : selectedCards) {
            hand.remove(c);
        }
        onCardsRemovedByPlayer.publish(new Tuple<Player,List<ICard>>(this,selectedCards));
    }

    //To get the card a player wants to play
    public List<ICard> play() {
        saidUno = false;
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

    public IPublisher<Tuple<Player, List<ICard>>> getOnCardsAddedByPlayer() {
        return onCardsAddedByPlayer;
    }

    public IPublisher<Tuple<Player, List<ICard>>> getOnCardRemovedByPlayer() {
        return onCardsRemovedByPlayer;
    }
}
