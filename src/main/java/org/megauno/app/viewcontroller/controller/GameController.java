package org.megauno.app.viewcontroller.controller;

import org.megauno.app.model.cards.Color;


/**
 * An interface using ID:s for players used for communicating with the game.
 */
public interface GameController {
    void selectCard(int cardId);

    void unSelectCard(int cardId);

    void commenceForth();

    void sayUno();

    void setColor(Color color);

    void drawCard();
}
