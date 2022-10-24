package org.megauno.app.model.game.utilities;

import org.junit.Test;
import org.megauno.app.model.cards.ICard;

import static org.junit.Assert.*;

public class PileTest {

    Pile pile = new Pile();

    // Test that discarding a card results in that card getting on top
    @Test
    public void testDiscard() {
        ICard topCard = pile.getTop();
        pile.discard(topCard);
        assert(topCard.equals(pile.getTop()));
    }

    @Test
    public void getTop() {
    }
}