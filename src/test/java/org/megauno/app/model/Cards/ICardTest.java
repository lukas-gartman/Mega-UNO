package org.megauno.app.model.Cards;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.megauno.app.model.Cards.Impl.ActionCard;
import org.megauno.app.model.Cards.Impl.NumberCard;
import org.megauno.app.model.Game.Actions.ReverseAction;
import org.megauno.app.model.Game.Actions.TakeFourAction;
import org.megauno.app.model.Game.Actions.TakeTwoAction;
import org.megauno.app.model.Game.Actions.WildCardAction;
import org.megauno.app.model.Game.Game;
import org.megauno.app.model.Game.IActOnGame;
import org.megauno.app.model.Game.PlayerCircle;
import org.megauno.app.model.GameFactory;
import org.megauno.app.model.Player.Player;

import java.util.ArrayList;
import java.util.List;

public class ICardTest {

    @Before
    public void setUp() throws Exception {
        Player p = new Player(new ArrayList<>());
        List<Player> players = new ArrayList<>();
        players.add(p);
        g = GameFactory.createGame(new PlayerCircle());
    }

    ICard nc = new NumberCard(Color.BLUE, 3);
    ICard nc2 = new NumberCard(Color.BLUE, 3);
    ICard nc3 = new NumberCard(Color.RED, 9);
    ICard nc4 = new NumberCard(Color.GREEN, 3);
    ICard ac1 = new ActionCard(new ReverseAction(), Color.BLUE, CardType.REVERSECARD);
    ICard ac2 = new ActionCard(new ReverseAction(), Color.BLUE, CardType.REVERSECARD);
    ICard ac3 = new ActionCard(new TakeFourAction(), Color.RED, CardType.TAKETWO);
    ICard ac4 = new ActionCard(new TakeTwoAction(), Color.RED, CardType.TAKETWO);

    IActOnGame g;
    //ActionCard ac = new ActionCard()

    @Test
    public void testEquals() {
        Assert.assertEquals(nc, nc2);
        Assert.assertEquals(ac1, ac2);
    }

    @Test
    public void testHashCode() {
        Assert.assertNotNull(nc.hashCode());
        Assert.assertNotNull(ac1.hashCode());
    }

    @Test
    public void testToString() {
       Assert.assertNotNull(nc.toString());
       Assert.assertNotNull(ac1.toString());
    }

    @Test
    public void testCanBePlayed1() {
       assert (nc.canBePlayed(nc2));
       assert(nc2.canBePlayed(nc));
       assert(ac3.canBePlayed(ac4));
       assert(nc.canBePlayed(ac1));
       assert(ac1.canBePlayed(nc));
    }

    @Test
    public void testCanBePlayed2() {
        Assert.assertFalse(nc.canBePlayed(nc3));
        Assert.assertFalse(nc3.canBePlayed(nc));
        Assert.assertFalse(ac2.canBePlayed(ac3));

    }

    @Test
    public void testCanBePlayedOnMe() {
    }

    @Test
    public void testTestCanBePlayedOnMe() {
    }

    @Test
    public void testGetType() {
        assert(nc.getType() == CardType.NUMBERCARD);
    }

    @Test
    public void testGetColor() {
        assert(nc.getColor() == Color.BLUE);
    }

    @Test
    public void testCopyCard() {
        assert(nc.equals(nc.copyCard()));
        assert(ac1.equals(ac1.copyCard()));
    }
    @Test
    public void testGetNumber() {
        assert(nc.getNumber().equals(3));
        Assert.assertNull(ac1.getNumber());
    }

    @Test
    public void testActivate1() {
        // This should be "assert" later on
        Assert.assertFalse(ac1.activate(g));
    }

    @Test
    public void testActivate2() {
        assert (nc.activate(g));
    }

    @Test
    public void testSetColor() {
        Color newColor = Color.GREEN;
        nc.setColor(newColor);
        assert(nc.getColor() == Color.GREEN);
    }

    // Decide on using visit or canBePlayedOnMe
    @Test
    public void testCanBeStacked() {
        assert(nc.canBeStacked(nc4));
        assert(nc4.canBeStacked(nc));
        assert(ac1.canBeStacked(ac2));
        Assert.assertFalse(nc.canBeStacked(nc3));
        Assert.assertFalse(nc3.canBeStacked(nc));
        Assert.assertFalse(nc.canBeStacked(ac1));
        Assert.assertFalse(ac1.canBeStacked(ac3));
        Assert.assertFalse(ac2.canBeStacked(nc));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testColorIndex() {
        Color.getFromIndex(28);
    }

}