package org.megauno.app.model.Game.Utilities;

import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Player.Player;

import java.util.List;


/**
 * Nodes store a player and its neighbours.
 * Node acts as a facade for player, such that classes that wants to communicate with
 * players does so via this class.
 */
public class Node {
    private Player player;
    public Node nextNode;
    public Node previousNode;

    public Node(Player p) {
        this.player = p;
    }

    /**
     * Makes the player that the node holds play his hand.
     * @return the cards which the player the node holds wants to play.
     */
    public List<ICard> play(){
        return player.play();
    }

    /**
     * Gives the card to the player the node holds.
     * @param card The card to give the player.
     */
    public void giveCardToPlayer(ICard card){
        player.addCard(card);
    }

    /**
     *
     * @return The player the node holds
     */
    public Player getPlayer(){
        return player;
    }

    public int getHandSize(){
        return player.numOfCards();
    }

    /**
     * Checks if the player that the node holds have said uno
     * @return if the player have said uno or not
     */
    public boolean uno(){
        return player.uno();
    }

    public void sayUno(){
        player.sayUno();
    }

    public void unsayUno(){
        player.unsayUno();
    }

    public List<ICard> getHand(){
        return player.getCards();
    }

    public void selectCard(ICard c){
        player.selectCard(c);
    }


    public void removeSelected(){
        player.removeSelectedCardsFromHand();
        player.discardAllSelectedCards();
    }
}