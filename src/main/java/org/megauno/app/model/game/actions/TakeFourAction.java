package org.megauno.app.model.game.actions;

import org.megauno.app.model.cards.IAction;
import org.megauno.app.model.game.IActOnGame;
import org.megauno.app.model.game.utilities.PlayerCircle;

/**
 * When this action is executed, the next player in line has to draw four cards
 */
public class TakeFourAction implements IAction {

    @Override
    public boolean execute(IActOnGame g) {
        for (int i = 0; i < 4; i++) {
            g.nextDraw();
        }
        return true;
    }
}
