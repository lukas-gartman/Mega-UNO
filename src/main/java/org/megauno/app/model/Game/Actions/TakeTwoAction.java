package org.megauno.app.model.Game.Actions;

import org.megauno.app.model.Cards.IAction;
import org.megauno.app.model.Deck;
import org.megauno.app.model.Game.PlayerCircle;

public class TakeTwoAction implements IAction {
    Deck deck;
    PlayerCircle players;

    public TakeTwoAction(){
    }
    @Override
    public boolean execute() {
        players.getNextPlayer().addCard(deck.drawCard());
        players.getNextPlayer().addCard(deck.drawCard());
        return true;
    }
}
