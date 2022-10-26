package org.megauno.app.viewcontroller.controller;

import org.megauno.app.model.cards.Color;


/**
 * An interface using ID:s for players used for communicating with the game.
 */
public interface GameController {
    /**
     * Selects the card with the given ID.
     *
     * @param cardId the ID of the card to select
     */
    void selectCard(int cardId);

    /**
     * Unselects the card with the given ID.
     *
     * @param cardId the ID of the card to unselect
     */
    void unSelectCard(int cardId);

    /**
     * Tries to play currently selected cards, does nothing if not.
     */
    void commenceForth();

    /**
     * Says makes player say UNO!
     */
    void sayUno();

    /**
     * Sets the color of all selected wildcards.
     *
     * @param color the colour to be set
     */
    void setColor(Color color);

    /**
     * Makes player draw a card from the draw pile.
     */
    void drawCard();

    /**
     * Send the player's nickname to the server
     *
     * @param nickname the nickname to be sent
     */
    void sendNickname(String nickname);
}
