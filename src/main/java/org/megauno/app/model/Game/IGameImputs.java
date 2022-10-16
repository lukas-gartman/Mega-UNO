package org.megauno.app.model.Game;

import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Player.Player;
import org.megauno.app.network.PlayersCards;

public interface IGameImputs {
    void selectCard(Player player, ICard card);

    void unSelectCard(Player player, ICard card);

    void commenceForth(Player player);

    void sayUno(Player player);
}
