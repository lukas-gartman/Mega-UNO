package org.megauno.app.model.Player;

import org.megauno.app.model.Cards.ICard;

public interface DecisionMaker {
    ICard chooseCard();
    boolean saidUno();
}
