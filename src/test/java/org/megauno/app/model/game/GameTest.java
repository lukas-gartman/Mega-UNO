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
import org.megauno.app.utility.Publisher.IPublisher;
import org.megauno.app.utility.Publisher.normal.Publisher;
import org.megauno.app.utility.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Flow;

public class GameTest {

    PlayerCircle players;
    Game game;

    Player p1;

    @Before
    public void setUp() {
        Game testGame = new Game();
        players = new PlayerCircle(generatePlayers(5));
        game = new Game(players);
        game.start(7);
        p1 = game.getCurrentPlayer();
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

    @Test
    public void testUpdate() {
        game.update();
    }

    @Test
    public void testTry_play() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(p1.numOfCards());
        p1.selectCard(p1.getCards().get(randomIndex));
        game.try_play();
    }

    @Test
    public void testCanBeStackedOn() {
        //assertFalse(game.validPlayedCards());
    }

    // Try that wrong cards cannot be stacked on top of each other.
    private void addSelectedCards(int nCards) {
        for (ICard c : p1.getCards()) {
            p1.selectCard(c);
        }
    }

    @Test
    public void testTryPlaySelectedCards() throws InterruptedException {
        addSelectedCards(3);
      //  game.tryPlayTest(); // an error expected
    }

    @Test
    public void testPublishers() {
        IPublisher<Player> onNewPlayer = new Publisher<>();
        assert(game.onNewPlayer().equals(onNewPlayer));

        IPublisher<ICard> onNewTopCard = new Publisher<>();
        assert(game.onNewTopCard().equals(onNewTopCard));

        IPublisher<Tuple<Player, List<ICard>>> onCardsAddedToP = new Publisher<>();
        assert(game.onCardsAddedToPlayer().equals(onCardsAddedToP));

        IPublisher<Tuple<Player, List<ICard>>> onCardsRemovedByP = new Publisher<>();
        assert(game.onCardsRemovedByPlayer().equals(onCardsRemovedByP));
    }

    @Test
    public void testSelectCard() {
        ICard card = p1.getCards().get(0);
        game.selectCard(p1, card);
        List<ICard> p1SelectedCards = p1.getSelectedCards();
        assert(p1SelectedCards.contains(card));
    }

    @Test
    public void testUnSelectCard() {
        ICard card = p1.getCards().get(1);
        game.selectCard(p1, card);
        game.unSelectCard(p1, card);
        List<ICard> p1SelectedCards = p1.getSelectedCards();
        Assert.assertFalse(p1SelectedCards.contains(card));
    }

    @Test
    public void testCommenceForth() {
        System.out.println(p1);
        game.commenceForth(p1);
        game.update();
        System.out.println(game.getCurrentPlayer());
        // Assumes that the simulated turn was not valid
        // thus no new player in turn yet
        assert(game.getCurrentPlayer() == p1);
    }

    @Test
    public void testSayUno() {
        game.sayUno(p1);
        assert(p1.uno());
    }

    @Test
    public void testDrawOneCard() {
        int before = p1.numOfCards();
        game.drawCard(p1);
        int after = p1.numOfCards();
        assert(before == after - 1);
    }

    @Test
    public void testDrawThreeCards() {
        int before = p1.numOfCards();
        // The forth time a player tries to draw, it moves on to the next player.
        for (int i = 0; i < 4; i++) {
            game.drawCard(p1);
        }
        int after = p1.numOfCards();
        assert(before == after - 3);
        assert(game.getCurrentPlayer() != p1);
    }


}