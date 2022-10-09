package org.megauno.app.model.Game.Actions;

import org.megauno.app.model.Cards.IAction;
import org.megauno.app.model.Deck;
import org.megauno.app.model.Game.IActOnGame;
import org.megauno.app.model.Game.PlayerCircle;

public class TakeTwoAction implements IAction {

    public TakeTwoAction(){
    }
    @Override
    public boolean execute(IActOnGame g) {
        g.getPlayers().getNextPlayer().addCard(g.getDeck().drawCard());
        g.getPlayers().getNextPlayer().addCard(g.getDeck().drawCard());

        return true;
    }
}
