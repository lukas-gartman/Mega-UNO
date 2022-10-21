package org.megauno.app.model.cards;

import org.megauno.app.model.cards.implementation.ActionCard;
import org.megauno.app.model.cards.implementation.NumberCard;

/**
 * An implementation of Visitor Pattern. This makes it possible to dynamically determine
 * what particular type of card that is passed an argument. This behavior is desired since
 * there are different rules to which type of card can be placed on another type of card.
 */
public interface ICardVisitor {

    /**
     * Checks that the given card can be played on this card.
     *
     * @param actionCard The card that wants to be played on this card
     * @return Weather it can be played on this or not.
     */
    boolean canBePlayedOnMe(ActionCard actionCard);

    /**
     * Checks that the given card can be played on this card.
     *
     * @param numberCard The card that wants to be played on this card
     * @return Weather it can be played on this or not.
     */
    boolean canBePlayedOnMe(NumberCard numberCard);

    /**
     * Checks that the given card can be stacked on this card.
     *
     * @param actionCard The card that wants to be stacked on this card
     * @return Weather it can be stacked on this or not.
     */
    boolean canBeStackedUnder(ActionCard actionCard);

    /**
     * Checks that the given card can be stacked on this card.
     *
     * @param numberCard The card that wants to be stacked on this card
     * @return Weather it can be stacked on this or not.
     */
    boolean canBeStackedUnder(NumberCard numberCard);
}
