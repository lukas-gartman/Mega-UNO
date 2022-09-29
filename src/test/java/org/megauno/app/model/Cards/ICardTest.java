package org.megauno.app.model.Cards;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.megauno.app.model.Cards.Impl.ActionCard;
import org.megauno.app.model.Cards.Impl.NumberCard;
import org.megauno.app.model.Game.Actions.ReverseAction;

public class ICardTest {

    @Before
    public void setUp() throws Exception {
    }

    ICard nc = new NumberCard(Color.BLUE, 3);
    ICard nc2 = new NumberCard(Color.BLUE, 3);
    ICard nc3 = new NumberCard(Color.RED, 9);
    ICard ac1 = new ActionCard(new ReverseAction(), Color.BLUE, CardType.REVERSECARD);
    //ActionCard ac = new ActionCard()

    @Test
    public void testTestEquals() {
        Assert.assertEquals(nc, nc2);
    }

    @Test
    public void testTestHashCode() {
        Assert.assertNotNull(nc.hashCode());
    }

    @Test
    public void testTestToString() {
       Assert.assertNotNull(nc.toString());
    }

    @Test
    public void testCanBePlayed1() {
       Assert.assertTrue(nc.canBePlayed(nc2));
       Assert.assertTrue(nc2.canBePlayed(nc));
    }

    @Test
    public void testCanBePlayed2() {
        Assert.assertFalse(nc.canBePlayed(nc3));
        Assert.assertFalse(nc3.canBePlayed(nc));
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
    }

    @Test
    public void testActivate1() {
        // This should be "assert" later on
        Assert.assertFalse(ac1.activate());
    }

    @Test
    public void testActivate2() {
        Assert.assertFalse(nc.activate());
    }
}