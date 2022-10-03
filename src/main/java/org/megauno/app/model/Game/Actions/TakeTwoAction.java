package org.megauno.app.model.Game.Actions;

import org.megauno.app.model.Cards.IAction;
import org.megauno.app.model.Deck;
import org.megauno.app.model.Game.Game;
import org.megauno.app.model.Game.PlayerCircle;

public class TakeTwoAction implements IAction {
    Deck deck;
    PlayerCircle players;

    public TakeTwoAction(Deck d, PlayerCircle p){
        deck = d;
        players = p;
    }
    @Override
    public boolean execute() {
        players.nextPlayer().addCard(deck.drawCard());
        players.nextPlayer().addCard(deck.drawCard());
        return true;
    }
}
