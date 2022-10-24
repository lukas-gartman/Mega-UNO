package org.megauno.app.model.game.actions;

import org.megauno.app.model.cards.IAction;
import org.megauno.app.model.game.IActOnGame;
import org.megauno.app.model.game.utilities.PlayerCircle;

/**
 * When this action is executed, the next player in line has to draw two cards.
 */
public class TakeTwoAction implements IAction {

    @Override
    public boolean execute(IActOnGame g) {
        g.nextDraw();
        g.nextDraw();
        return true;
    }
}
