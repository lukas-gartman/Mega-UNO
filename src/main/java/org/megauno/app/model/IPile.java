package org.megauno.app.model;

import org.megauno.app.model.Cards.ICard;

/**
 * A pile for discarding cards
 * @author Lukas Gartman
 */
public interface IPile {
    /**
     * Discard a card to the pile
     * @param card the card to be discarded
     */
    void discard(ICard card);

    /**
     * Get the card on top of the file
     * @return the top card
     */
    ICard getTop();
}
