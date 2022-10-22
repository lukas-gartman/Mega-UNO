package org.megauno.app.model.game;

import org.megauno.app.model.cards.ICard;
import org.megauno.app.model.player.Player;

public class GameStatePrint {
    public static void print(Game game){
        printCard(game.getTopCard());
        for (Player player:game.getPlayers()) {
            if(player == game.getCurrentPlayer()){
                System.out.println("It is my turn");
            }
            System.out.println("Player: " + player);
            System.out.println("Cards on hand");
            for(ICard card: player.getCards()){
                printCard(card);
            }
            System.out.println("Cards selected");
            for (ICard card:player.getSelectedCards()) {
                printCard(card);
            }
            System.out.println("Has said uno: " + player.uno());
        }
    }
    public static void printCard(ICard card){
        System.out.println("Card: " + card);
    }
}
