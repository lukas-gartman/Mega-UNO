package org.megauno.app.model.Game;

import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Player.Player;

import java.util.List;

// Nodes are players and their neighbours
public class Node<c> {
    private IPlayer player;
    public Node nextNode;
    public Node previousNode;

    public Node(IPlayer p) {
        this.player = p;
    }

    public List<c> play(){
        return player.play();
    }

    void giveCardToPlayer(c item){
        player.addCard(item);
    }

    public IPlayer getPlayer(){
        return player;
    }
}
