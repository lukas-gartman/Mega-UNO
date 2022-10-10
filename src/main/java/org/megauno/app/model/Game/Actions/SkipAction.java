package org.megauno.app.model.Game.Actions;

import org.megauno.app.model.Cards.IAction;
import org.megauno.app.model.Game.IActOnGame;
import org.megauno.app.model.Game.PlayerCircle;

public class SkipAction implements IAction {
    public SkipAction(){
    }

    @Override
    public boolean execute(IActOnGame g) {
        g.getPlayers().moveOnToNextTurn();
        return true;
    }
}