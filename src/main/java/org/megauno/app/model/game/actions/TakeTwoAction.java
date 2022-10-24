package org.megauno.app.model.game.actions;

import org.megauno.app.model.cards.IAction;
import org.megauno.app.model.game.IActOnGame;

/**
 * When this action is executed, the next player in line has to draw two cards.
 */
public class TakeTwoAction implements IAction {

    @Override
    public boolean execute(IActOnGame game) {
        game.nextDraw();
        game.nextDraw();
        return true;
    }
}
