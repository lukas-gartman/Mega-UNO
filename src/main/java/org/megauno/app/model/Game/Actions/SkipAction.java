package org.megauno.app.model.Game.Actions;

import org.megauno.app.model.Cards.IAction;
import org.megauno.app.model.Game.PlayerCircle;

public class SkipAction implements IAction {
    PlayerCircle players;

    public SkipAction(){
    }

    @Override
    public boolean execute() {
        players.nextTurn();
        return true;
    }
}
