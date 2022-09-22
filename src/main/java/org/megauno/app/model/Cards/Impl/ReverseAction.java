package org.megauno.app.model.Cards.Impl;

import org.megauno.app.model.Cards.IAction;
import org.megauno.app.model.Game.PlayerCircle;


public class ReverseAction implements IAction {

    PlayerCircle players;

    ReverseAction(PlayerCircle players){
        this.players = players;
    }
    @Override
    public boolean execute() {
        reverse();
        return true;
    }

    public void reverse(){
        players.changeRotation();
    }
}
