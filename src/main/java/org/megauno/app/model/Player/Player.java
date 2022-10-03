package org.megauno.app.model.Player;

import org.megauno.app.model.Cards.ICard;

import java.util.ArrayList;
import java.util.List;

import static org.megauno.app.utility.CardMethodes.copyCards;

//
public class Player {

    //Bool for if the player has said uno this round
    private final int id;
    private boolean saidUno = false;
    private ICard selectedCard;
    //The hand of the player, has the cards
    private ArrayList<ICard> hand;
    //The selected card
    public Player(ArrayList<ICard> hand, int id) {
        this.hand = hand;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Player(List<ICard> hand) {
        this.hand = hand;
    }

    public void selectCard(ICard c){
        selectedCard = c;
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

    public ArrayList<ICard> getCards(){
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
        saidUno = false;
        return selectedCard;
    }



}
