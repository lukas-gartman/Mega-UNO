package org.megauno.app.model.Game.Actions;

import org.megauno.app.model.Cards.IAction;
import org.megauno.app.model.Game.IActOnGame;
import org.megauno.app.model.Game.PlayerCircle;

public class TakeTwoAction implements IAction {

    public TakeTwoAction(){
    }
    @Override
    public boolean execute(IActOnGame g) {
        PlayerCircle players = g.getPlayers();
        players.giveCardToPlayer(g.draw(), players.getNextPlayerNode());
        players.giveCardToPlayer(g.draw(), players.getNextPlayerNode());

        return true;
    }
}
