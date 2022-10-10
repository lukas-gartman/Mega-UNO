package org.megauno.app.model.Game;

import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Deck;

public interface IActOnGame {
    PlayerCircle getPlayerCircle();
    Deck getDeck();
    ICard draw();

    void reverse();

    void nextTurn();
}
