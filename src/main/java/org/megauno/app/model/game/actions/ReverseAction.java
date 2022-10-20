package org.megauno.app.model.game.actions;

import org.megauno.app.model.cards.IAction;
import org.megauno.app.model.game.IActOnGame;

/**
 * A reverse action, which changes the order of play in the game.
 */
public class ReverseAction implements IAction {

    @Override
    public boolean execute(IActOnGame g) {
        // Changes the order of play in the game.
        g.reverse();
        return false;
    }
}
