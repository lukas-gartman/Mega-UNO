package org.megauno.app.model.Game;

import org.megauno.app.model.Cards.Color;
import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Game.Utilities.PlayerCircle;
import org.megauno.app.model.Player.Player;

public interface IActOnGame {
    PlayerCircle getPlayerCircle();

    Player[] getPlayers();

    IDeck getDeck();

    ICard draw();

    void reverse();

    void nextTurn();

    ICard getTopCard();

    Color getChosenColor();

    void sayUno(Player player);

    void unsayUno(Player player);
}
