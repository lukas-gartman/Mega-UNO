package org.megauno.app.model.game.actions;

import org.megauno.app.model.cards.IAction;
import org.megauno.app.model.game.IActOnGame;

/**
 * The action of a wildcard is that the player playing the wildcard can pick any color they like
 * to be played next
 */
public class WildCardAction implements IAction {

    @Override
    public boolean execute(IActOnGame g) {
        g.assignWildCardColor();
        return true;
    }
}