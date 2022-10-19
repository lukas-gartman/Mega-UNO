package org.megauno.app.model.Game;

import org.megauno.app.model.Cards.Color;
import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Player.Player;
import org.megauno.app.network.PlayersCards;

public interface IGameImputs {
    void selectCard(Player player, ICard card);

    void unSelectCard(Player player, ICard card);

    void commenceForth(Player player);

    void sayUno(Player player);

    void drawCard(Player player);

    void setColor(Player player, Color color);
}
