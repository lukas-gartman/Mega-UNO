package org.megauno.app.model.Game.Actions;

import org.megauno.app.model.Cards.IAction;
import org.megauno.app.model.Game.IActOnGame;
import org.megauno.app.model.Game.Utilities.PlayerCircle;

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
