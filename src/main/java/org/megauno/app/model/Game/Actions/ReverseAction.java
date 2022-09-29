package org.megauno.app.model.Game.Actions;

import org.megauno.app.model.Cards.IAction;
import org.megauno.app.model.Game.PlayerCircle;

public class ReverseAction implements IAction {
    PlayerCircle players;

    public ReverseAction(PlayerCircle players){
        this.players = players;
    }

    @Override
    public boolean execute() {
       // Something in the likes of: game.changeRoatation()
       // The question here is, should the action directly modify the game state as if they existed
       // inside the game class. These actions basically replicates this behavior, but does it in a more abstracted
       // manner
        players.changeRotation();
        return false;
    }
}
