package org.megauno.app.model.game;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.megauno.app.model.GeneralTestMethods;
import org.megauno.app.model.cards.CardType;
import org.megauno.app.model.cards.Color;
import org.megauno.app.model.cards.ICard;

import org.megauno.app.model.cards.implementation.ActionCard;
import org.megauno.app.model.cards.implementation.NumberCard;
import org.megauno.app.model.game.actions.WildCardAction;
import org.megauno.app.model.game.utilities.Deck;

import org.megauno.app.model.game.utilities.PlayerCircle;
import org.megauno.app.model.player.Player;
import org.megauno.app.utility.Publisher.ISubscribable;
import org.megauno.app.utility.Publisher.normal.Publisher;
import org.megauno.app.utility.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.megauno.app.model.GeneralTestMethods.generatePlayers;

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
    public void testValidPlayedTurn() {
        // Setup
        List<ICard> cardList = new ArrayList<>();
        cardList.add(new ActionCard(new WildCardAction(), Color.NONE, CardType.WILDCARD));
        PlayerCircle players = new PlayerCircle(generatePlayers(5));
        Game testGame = new Game(players);
        Player p1 = testGame.getCurrentPlayer();

        // Give the playable card to the player
        p1.addCards(cardList);
        // Make the player select the given card
        p1.selectCard(cardList.get(0));
        testGame.setColor(p1, Color.RED);
        // Start the game with no additional cards to players
        testGame.start(0);
        // Play one turn
        testGame.commenceForth(p1);
        // Check that the played round was successful.
        assert(testGame.getCurrentPlayer() != p1);
    }

    @Test
    public void testPlayUnstackableCards() {
        // Setup
        List<ICard> cardList = new ArrayList<>();
        ICard testCard1 = new NumberCard(Color.RED, 3);
        ICard testCard2 = new NumberCard(Color.GREEN, 6);
        cardList.add(testCard1);
        cardList.add(testCard2);
        PlayerCircle players = new PlayerCircle(generatePlayers(5));
        Game testGame = new Game(players);
        Player p1 = testGame.getCurrentPlayer();

        // Give playable cards to the player
        p1.addCards(cardList);
        // Select the cards
        p1.selectCard(cardList.get(0));
        p1.selectCard(cardList.get(1));
        // Start the game with no additional cards added to the players
        testGame.start(0);
        // Play one round
        testGame.commenceForth(p1);
        // Check that the played round wasn't successful.
        assert(testGame.getCurrentPlayer() == p1);
    }

    @Test
    public void testPlayStackableAndUnstackableCards() {
        // Setup
        List<ICard> cardList = new ArrayList<>();
        ICard testCard1 = new NumberCard(Color.RED, 3);
        ICard testCard2 = new NumberCard(Color.GREEN, 3);
        ICard testCard3 = new NumberCard(Color.BLUE, 4);
        cardList.add(testCard1);
        cardList.add(testCard2);
        cardList.add(testCard3);
        PlayerCircle players = new PlayerCircle(generatePlayers(5));
        Game testGame = new Game(players);
        Player p1 = testGame.getCurrentPlayer();

        // Give playable cards to the player
        p1.addCards(cardList);
        // Select the cards
        p1.selectCard(cardList.get(0));
        p1.selectCard(cardList.get(1));
        p1.selectCard(cardList.get(2));
        // Start the game with no additional cards added to the players
        testGame.start(0);
        // Play one round
        testGame.commenceForth(p1);
        // Check that the played round wasn't successful.
        assert(testGame.getCurrentPlayer() == p1);
    }

    @Test
    public void testDrawThreeCardsInTurn() {
        // Setup
        List<ICard> cardList = new ArrayList<>();
        ICard testCard = new ActionCard(new WildCardAction(), Color.NONE, CardType.WILDCARD);
        cardList.add(testCard);
        cardList.add(testCard);
        PlayerCircle players = new PlayerCircle(generatePlayers(5));
        Game testGame = new Game(players);
        Player p1 = testGame.getCurrentPlayer();

        // Assign the cards to the player
        p1.addCards(cardList);
        p1.selectCard(cardList.get(0));
        // Start the game with no additional cards to the players
        testGame.start(0);
        // Play one round
        testGame.commenceForth(p1);
        // Check that the played round was successfull.
        assert(testGame.getCurrentPlayer() != p1);
    }

    @Test
    public void testWinningTheGame() {
        // Setup
        List<ICard> cardList = new ArrayList<>();
        ICard testCard = new ActionCard(new WildCardAction(), Color.NONE, CardType.WILDCARD);
        ICard testCard2 = new ActionCard(new WildCardAction(), Color.NONE, CardType.WILDCARD);
        cardList.add(testCard);
        cardList.add(testCard2);
        PlayerCircle players = new PlayerCircle(generatePlayers(5));
        Game testGame = new Game(players);
        Player p1 = testGame.getCurrentPlayer();

        // Give playable cards to the player
        p1.addCards(cardList);
        // Select the cards
        p1.selectCard(cardList.get(0));
        p1.selectCard(cardList.get(1));
        // Say UNO! in order to be able to win
        p1.sayUno();
        // Start the game with no additional cards added to the players
        testGame.start(0);
        // Play one round
        testGame.commenceForth(p1);
        // Check that the round was successfull.
        assert(testGame.getCurrentPlayer() != p1);
    }

    @Test
    public void testNextDraws(){
        Player next = players.getNextPlayer();
        List<ICard> nextHand = new ArrayList<>();
        for (ICard c : next.getCards()) nextHand.add(c);
        game.nextDraw();
        List<ICard> nextHandAfterDraw = next.getCards();
        assert(!nextHandAfterDraw.equals(nextHand));
    }

    @Test
    public void testAssignWildCardColor(){
        p1.discardAllSelectedCards();
        ICard c = new ActionCard(new WildCardAction(), Color.NONE,CardType.WILDCARD);
        p1.addCard(c);
        p1.selectCard(c);
        game.setColor(p1, Color.BLUE);
        game.commenceForth(p1);
        assert(game.getTopCard().getColor()==Color.BLUE && c.getColor() == Color.BLUE);
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
        ISubscribable<Player> onNewPlayer = new Publisher<>();
        assert(game.onNewPlayer().equals(onNewPlayer));

        ISubscribable<ICard> onNewTopCard = new Publisher<>();
        assert(game.onNewTopCard().equals(onNewTopCard));

        ISubscribable<Tuple<Player, List<ICard>>> onCardsAddedToP = new Publisher<>();
        assert(game.onCardsAddedToPlayer().equals(onCardsAddedToP));

        ISubscribable<Tuple<Player, List<ICard>>> onCardsRemovedByP = new Publisher<>();
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
        game.playerDraws(p1);
        int after = p1.numOfCards();
        assert(before == after - 1);
    }

    @Test
    public void testDrawThreeCards() {
        int before = p1.numOfCards();
        // The forth time a player tries to draw, it moves on to the next player.
        for (int i = 0; i < 4; i++) {
            game.playerDraws(p1);
        }
        int after = p1.numOfCards();
        assert(before == after - 3);
        assert(game.getCurrentPlayer() != p1);
        // Try playing the last drawn card
        p1.selectCard(p1.getCards().get(p1.numOfCards() - 1));
        game.commenceForth(p1);
    }


}