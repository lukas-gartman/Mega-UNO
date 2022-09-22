package org.megauno.app.model.Cards;

import junit.framework.TestCase;
import org.megauno.app.model.Cards.Impl.NumberCard;

public class ICardTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
    }

    ICard nc = new NumberCard(Color.BLUE, 3);
    ICard nc2 = new NumberCard(Color.BLUE, 3);
    ICard nc3 = new NumberCard(Color.RED, 9);
    //ActionCard ac = new ActionCard()

    public void testTestEquals() {
        assertEquals(nc, nc2);
    }

    public void testTestHashCode() {
    }

    public void testTestToString() {
       //nc.visit(nc2);
    }

    public void testCanBePlayed1() {
       assertTrue(nc.canBePlayed(nc2));
       assertTrue(nc2.canBePlayed(nc));
    }

    public void testCanBePlayed2() {
        assertFalse(nc.canBePlayed(nc3));
        assertFalse(nc3.canBePlayed(nc));
    }

    public void testCanBePlayedOnMe() {
    }

    public void testTestCanBePlayedOnMe() {
    }

    public void testGetType() {
    }

    public void testGetColor() {
    }

    public void testCopyCard() {
    }
}