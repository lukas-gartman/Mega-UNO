package org.megauno.app.model.Game;

import org.megauno.app.model.Cards.Color;
import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Player.Player;

/**
 * The communication that is done from the application to the game
 */
public interface IGameImputs {
    /**
     * A given player wants to select a card.
     * @param player The player that wants to select.
     * @param card The card the player wants to select.
     */
    void selectCard(Player player, ICard card);

    /**
     * A given player wants to unselect a card.
     * @param player The player that wants to unselect.
     * @param card The card the player wants to unselect.
     */
    void unSelectCard(Player player, ICard card);

    /**
     * The signal from the application that tells the model that a round should be played.
     * @param player The player that wants to play the round.
     */
    void commenceForth(Player player);

    /**
     * A player wants to say UNO!
     * @param player The player that wants to say UNO!
     */
    void sayUno(Player player);

    /**
     * A player should draw a card.
     * @param player the player that should draw a card.
     */
    void drawCard(Player player);

    /**
     * When a wildcard is played a player should be able to choose the color of the wildcard.
     * @param player The player that played the wildcard
     * @param color The color the player wants to set the wildcard to.
     */
    void setColor(Player player, Color color);
}
