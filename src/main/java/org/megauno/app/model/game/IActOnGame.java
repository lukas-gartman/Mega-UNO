package org.megauno.app.model.game;

import org.megauno.app.model.cards.Color;
import org.megauno.app.model.cards.ICard;
import org.megauno.app.model.game.utilities.PlayerCircle;
import org.megauno.app.model.player.Player;

/**
 * This interface is used for abstracting game from components in the model that wants to interact
 * with the game in one way or another.
 * The interface is only used by actions.
 */
public interface IActOnGame {

    /**
     *
     * @return the player circle in the game
     */
    PlayerCircle getPlayerCircle();

    /**
     *
     * @return an array of the players in the player circle.
     */
    Player[] getPlayers();

    /**
     * Draw a card from the deck in the game.
     * @return the card that is drawn.
     */
    ICard draw();

    /**
     * Reverses the order of play in the game.
     */
    void reverse();

    /**
     * Moves on to the next player in turn.
     */
    void nextTurn();

    /**
     *
     * @return the top card from the pile
     */
    ICard getTopCard();

    /**
     *
     * @return the chosen that a player has set.
     */
    Color getChosenColor();

    /**
     * Th player says uno.
     * @param player the player that wants to say uno.
     */
    void sayUno(Player player);

}
