package org.megauno.app.model.Game.Actions;

import org.megauno.app.model.Cards.IAction;
import org.megauno.app.model.Game.IActOnGame;
import org.megauno.app.model.Game.PlayerCircle;

public class TakeFourAction implements IAction {


    public TakeFourAction(){

    }

    @Override
    public boolean execute(IActOnGame g){
        PlayerCircle players = g.getPlayers();
        players.giveCardToPlayer(g.getDeck().drawCard(), players.getNextPlayerNode());
        players.giveCardToPlayer(g.getDeck().drawCard(), players.getNextPlayerNode());
        players.giveCardToPlayer(g.getDeck().drawCard(), players.getNextPlayerNode());
        players.giveCardToPlayer(g.getDeck().drawCard(), players.getNextPlayerNode());



        return true;
    }
}
