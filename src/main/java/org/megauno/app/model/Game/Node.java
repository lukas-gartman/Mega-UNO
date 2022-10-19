package org.megauno.app.model.Game;

import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Player.Player;

import java.util.List;

// Nodes are players and their neighbours
public class Node {
    private Player player;
    public Node nextNode;
    public Node previousNode;

    public Node(Player p) {
        this.player = p;
    }

    public List<ICard> play(){
        return player.play();
    }

    void giveCardToPlayer(ICard card){
        player.addCard(card);
    }

    public Player getPlayer(){
        return player;
    }

    public int getHandSize(){
        return player.numOfCards();
    }

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