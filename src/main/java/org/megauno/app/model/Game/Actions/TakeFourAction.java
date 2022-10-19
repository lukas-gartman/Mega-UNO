package org.megauno.app.model.Game.Actions;

import org.megauno.app.model.Cards.IAction;
import org.megauno.app.model.Game.IActOnGame;
import org.megauno.app.model.Game.PlayerCircle;

/**
 * When this action is executed, the next player in line has to draw four cards
 */
public class TakeFourAction implements IAction {

    @Override
    public boolean execute(IActOnGame g){
        PlayerCircle players = g.getPlayerCircle();
        for (int i = 0; i < 4; i++) {
            players.giveCardToPlayer(g.draw(), players.getNextPlayerNode());
        }
        return true;
    }
}
