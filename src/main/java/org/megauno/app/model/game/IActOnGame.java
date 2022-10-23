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
     * Draw a card from the deck in the game.
     * @return the card that is drawn.
     */
    void nextDraw();

    /**
     * Reverses the order of play in the game.
     */
    void reverse();

    /**
     * Moves on to the next player in turn.
     */
    void nextTurn();

    /**
     * assigns the topCard the chosen color
     */
    void assignColor();

}
