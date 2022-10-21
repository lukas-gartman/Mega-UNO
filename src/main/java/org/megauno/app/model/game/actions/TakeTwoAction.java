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
        PlayerCircle players = g.getPlayerCircle();
        players.giveCardToPlayer(g.draw(), players.getNextPlayerNode());
        players.giveCardToPlayer(g.draw(), players.getNextPlayerNode());
        return true;
    }
}
