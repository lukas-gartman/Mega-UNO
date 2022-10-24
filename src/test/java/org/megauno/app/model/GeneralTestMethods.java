package org.megauno.app.model;

import org.megauno.app.model.cards.ICard;
import org.megauno.app.model.game.utilities.PlayerCircle;
import org.megauno.app.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class GeneralTestMethods {

    public static PlayerCircle generatePlayerCircle(int n) {
        List<Player> playerList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            playerList.add(new Player());
        }
        return new PlayerCircle(playerList);
    }

    public static List<Player> generatePlayers(int n) {
        List<Player> playerList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            playerList.add(new Player());
        }
        return playerList;
    }

    public static void giveCardsToPlayer(List<ICard> cards, Player player) {
        player.addCards(cards);
    }
}
