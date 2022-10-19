package org.megauno.app.model;

import org.megauno.app.model.Cards.ICard;

import java.util.List;

/**
 * A deck for drawing cards
 * @author Lukas Gartman
 */
public interface IDeck {
    /**
     * Draw a card from the deck
     * @return the card to be drawn
     */
    ICard drawCard();

    /**
     * Deal a hand of cards
     * @param sizeHand the number of cards
     * @return a list of ICard with the specified number of cards
     */
    List<ICard> dealHand(int sizeHand);
}
