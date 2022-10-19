package org.megauno.app.model.Game.Actions;

import org.megauno.app.model.Cards.Color;
import org.megauno.app.model.Cards.IAction;
import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Game.IActOnGame;


// Wild card is a type not an actioncard.
public class WildCardAction implements IAction{

    public WildCardAction(){
    }

    @Override
    public boolean execute(IActOnGame g) {
        assignColor(g.getChosenColor(), g.getTopCard());
        return true;
    }

    // to be called by client when choosing to play an actionCard.
    public void assignColor(Color choice, ICard c){
        c.setColor(choice);
    }

}