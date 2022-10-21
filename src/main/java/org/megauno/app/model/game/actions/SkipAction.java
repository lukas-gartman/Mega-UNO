package org.megauno.app.model.game.actions;

import org.megauno.app.model.cards.IAction;
import org.megauno.app.model.game.IActOnGame;

/**
 * This action skips the next player in line.
 */
public class SkipAction implements IAction {

    @Override
    public boolean execute(IActOnGame g) {
        g.nextTurn();
        return true;
    }
}
