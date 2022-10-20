package org.megauno.app.model.game.actions;

import org.megauno.app.model.cards.Color;
import org.megauno.app.model.cards.IAction;
import org.megauno.app.model.cards.ICard;
import org.megauno.app.model.game.IActOnGame;

/**
 * The action of a wildcard is that the player playing the wildcard can pick any color they like
 * to be played next
 */
public class WildCardAction implements IAction {

    @Override
    public boolean execute(IActOnGame g) {
        assignColor(g.getChosenColor(), g.getTopCard());
        return true;
    }

    // to be called by client when choosing to play an actionCard.
    public void assignColor(Color choice, ICard c) {
        c.setColor(choice);
    }
    // Wild card is a type not an actioncard.
}