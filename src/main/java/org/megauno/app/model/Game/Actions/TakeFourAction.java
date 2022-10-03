package org.megauno.app.model.Game.Actions;

import org.megauno.app.model.Cards.IAction;
import org.megauno.app.model.Deck;
import org.megauno.app.model.Game.PlayerCircle;

public class TakeFourAction implements IAction {
    Deck deck;
    PlayerCircle players;

    public TakeFourAction(Deck d, PlayerCircle p){
        deck = d;
        players = p;
    }

    @Override
    public boolean execute(){
        players.nextPlayer().addCard(deck.drawCard());
        players.nextPlayer().addCard(deck.drawCard());
        players.nextPlayer().addCard(deck.drawCard());
        players.nextPlayer().addCard(deck.drawCard());
        return true;
    }
}
