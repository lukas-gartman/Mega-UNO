package org.megauno.app.model.game;

/**
 * This interface is used for abstracting game from components in the model that wants to interact
 * with the game in one way or another.
 * The interface is only used by actions.
 */
public interface IActOnGame {
    /**
     * Draw a card from the deck in the game.
     *
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
     * Assign a colour to the wild card
     */
    void assignWildCardColor();

}
