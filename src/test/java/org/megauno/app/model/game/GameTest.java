package org.megauno.app.model.game;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.megauno.app.model.cards.Color;
import org.megauno.app.model.cards.ICard;
import org.megauno.app.model.game.utilities.Deck;
import org.megauno.app.model.game.utilities.PlayerCircle;
import org.megauno.app.model.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameTest extends TestCase {

    Deck deck = new Deck();
    PlayerCircle players;
    Game game;

    @Before
    public void setUp() {
        players = new PlayerCircle(generatePlayers(5));
        game = new Game(players);
        game.start(7);
    }

    public List<Player> generatePlayers(int n) {
        List<Player> playerList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            playerList.add(new Player());
        }
        return playerList;
    }

    @Test
    public void testTryPlay() {
        game.try_play();
    }

    @Test
    public void testNextTurn() {
        Player previousPlayer = game.getCurrentPlayer();
        game.nextTurn();
        Player currentPlayer = game.getCurrentPlayer();
        Assert.assertNotEquals(previousPlayer, currentPlayer);
    }

    @Test
    public void testSetColor() {
        game.setColor(game.getCurrentPlayer(), Color.BLUE);
        assert(game.getChosenColor() == Color.BLUE);
    }

    @Test
    public void testCheckPlayerProgress() {
        
    }

    public void testUpdate() {
        game.update();
    }

    public void testTry_play() {
        Random rand = new Random();
        Player currentPlayer = players.getCurrent().getPlayer();
        int randomIndex = rand.nextInt(currentPlayer.numOfCards());
        currentPlayer.selectCard(currentPlayer.getCards().get(randomIndex));
        game.try_play();
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

    public void testTryPlaySelectedCards() throws InterruptedException {
        addSelectedCards(3);
      //  game.tryPlayTest(); // an error expected
    }


}