package org.megauno.app.model.Game;

import org.megauno.app.model.Cards.Color;
import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Deck;
import org.megauno.app.model.IDeck;
import org.megauno.app.model.Player.Player;

public interface IActOnGame {
    PlayerCircle getPlayerCircle();

    Player[] getPlayers();

    Player getPlayerWithId(int id);

    IDeck getDeck();

    ICard draw();

    void reverse();

    void nextTurn();

    ICard getTopCard();

    Color getChosenColor();

    void setColor(Color color);

    void sayUno(Player player);

    void unsayUno(Player player);
}
