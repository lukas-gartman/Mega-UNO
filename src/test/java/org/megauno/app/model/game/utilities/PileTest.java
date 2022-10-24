package org.megauno.app.model.game.utilities;

import org.junit.Test;
import org.megauno.app.model.cards.ICard;


public class PileTest {

    public Pile pile = new Pile();

    // Test that discarding a card results in that card getting on top
    @Test
    public void testDiscard() {
        ICard topCard = pile.getTop();
        pile.discard(topCard);
        assert(topCard.equals(pile.getTop()));
    }
}