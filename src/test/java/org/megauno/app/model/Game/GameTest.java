package org.megauno.app.model.Game;

import junit.framework.TestCase;
import org.lwjgl.system.CallbackI;
import org.megauno.app.model.Deck;
import org.megauno.app.model.Player.Player;

import java.util.ArrayList;
import java.util.List;

public class GameTest extends TestCase {

    Deck deck = new Deck();
    PlayerCircle players = new PlayerCircle(generatePlayers(5));
    Game game = new Game(players);

    public List<Player> generatePlayers(int n) {
        List<Player> playerList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            playerList.add(new Player(deck.dealHand()));
        }
        return playerList;
    }

    public void testUpdate() {
        game.update();
    }

    public void testReverse() {
    }

    public void testTry_play() {
    }
}