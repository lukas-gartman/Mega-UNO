package org.megauno.app.model.Game.Actions;

import org.megauno.app.model.Cards.IAction;
import org.megauno.app.model.Game.IActOnGame;

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
