package org.megauno.app.model.game.actions;

import org.megauno.app.model.cards.IAction;
import org.megauno.app.model.game.IActOnGame;

/**
 * When this action is executed, the next player in line has to draw four cards
 */
public class TakeFourAction implements IAction {

    @Override
    public boolean execute(IActOnGame game) {
        for (int i = 0; i < 4; i++) {
            game.nextDraw();
        }
        return true;
    }
}
