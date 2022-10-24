package org.megauno.app.viewcontroller.controller;

import org.megauno.app.model.cards.Color;


/**
 * An interface using ID:s for players used for communicating with the game.
 */
public interface GameController {
	/**
	 * Selects the card with the given ID.
	 */
    void selectCard(int cardId);

	/**
	 * Unselects the card with the given ID.
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
	 */
    void setColor(Color color);

	/**
	 * Makes player draw a card from the draw pile.
	 */
    void drawCard();
}
