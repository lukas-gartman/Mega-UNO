package org.megauno.app.model.Game.Actions;

import org.megauno.app.model.Cards.IAction;
import org.megauno.app.model.Game.IActOnGame;

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
