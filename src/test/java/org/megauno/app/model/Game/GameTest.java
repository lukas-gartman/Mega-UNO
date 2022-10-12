package org.megauno.app.model.Game;

import junit.framework.TestCase;
import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Deck;
import org.megauno.app.model.GameFactory;
import org.megauno.app.model.Player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameTest extends TestCase {

    Deck deck = new Deck();
    PlayerCircle players = new PlayerCircle(generatePlayers(5));
    Game game = new Game(players);

    GameFactory gameFactory = new GameFactory();

    public List<Player> generatePlayers(int n) {
        List<Player> playerList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            playerList.add(new Player(0));
        }
        return playerList;
    }

    public void testUpdate() {
        game.update();
    }

    public void testReverse() {
    }

    public void testTry_play() {
        Random rand = new Random();
        Player currentPlayer = players.getCurrent().getPlayer();
        int randomIndex = rand.nextInt(currentPlayer.numOfCards());
        currentPlayer.selectCard(currentPlayer.getCards().get(randomIndex));
        game.try_play();
    }

    public void testPlayRandomTurn() {
        for (int i = 0; i < 40; i++) {
            game.tryPlayTest();
        }
        //assert(game.tryPlayTest());
    }

    public void testCanBeStackedOn() {
        //assertFalse(game.validPlayedCards());
    }

    // Try that wrong cards cannot be stacked on top of each other.
    private void addSelectedCards(int nCards) {
        Player currentPlayer = game.getPlayerCircle().getCurrent().getPlayer();
        for (ICard c : currentPlayer.getCards()) {
            currentPlayer.selectCard(c);
        }
    }

    public void testTryPlaySelectedCards() {
        addSelectedCards(3);
        game.tryPlayTest(); // an error expected
    }


}