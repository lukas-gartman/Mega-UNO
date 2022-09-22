package org.megauno.app.model.Game;

import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Player.Player;

// Nodes are players and their neighbours
public class Node {
    private Player player;
    public Node nextNode;
    public Node previousNode;

    public Node(Player p) {
        this.player = p;
    }

    public ICard play(ICard top){
        return player.play(top);
    }

    void giveCardToPlayer(ICard choice){
        player.addCard(choice);
    }

    Player getPlayer(){
        return player;
    }
}
