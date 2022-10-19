package org.megauno.app.model.Cards;

import org.megauno.app.model.Cards.Impl.ActionCard;
import org.megauno.app.model.Cards.Impl.NumberCard;
import org.megauno.app.model.Game.IActOnGame;

/**
 * Describes the behavior of a Card in the game of UNO.
 */
public interface ICard extends ICardVisitor {

    boolean equals(Object o);

    int hashCode();

    String toString();

    /**
     * Checks that this card can be played on the given card.
     * @param card  The card that is played on.
     * @return If the card can be played or not.
     */
    boolean canBePlayed(ICard card);

    /**
     * Visitor Pattern. Checks that the given card can be played on this card.
     * @param numberCard The card that wants to be played on this card
     * @return Weather it can be played on this or not.
     */
    boolean canBePlayedOnMe(NumberCard numberCard);

    /**
     * Visitor Pattern. Checks that the given card can be played on this card.
     * @param actionCard The card that wants to be played on this card
     * @return Weather it can be played on this or not.
     */
    boolean canBePlayedOnMe(ActionCard actionCard);

    CardType getType();

    Color getColor();

    ICard copyCard();

	Integer getNumber();

    /**
     * Propagates the changes a particular card has on the game.
     * @param g An abstraction of a game.
     * @return Weather the change was successfully done.
     */
    boolean activate(IActOnGame g);

    void setColor(Color choice);

    /**
     * Checks weather a card can be stacked on another.
     * This is relevant when multiple cards are chosen at once.
     * @param card The card which this card wants to stack upon.
     * @return If this card can be stacked on the given card.
     */
    boolean canBeStacked(ICard card);

    /**
     * Visitor Pattern. Checks that the given card can be stacked on this card.
     * @param numberCard The card that wants to be stacked on this card
     * @return Weather it can be stacked on this or not.
     */
    boolean canBeStackedUnder(NumberCard numberCard);

    /**
     * Visitor Pattern. Checks that the given card can be stacked on this card.
     * @param actionCard The card that wants to be stacked on this card
     * @return Weather it can be stacked on this or not.
     */
    boolean canBeStackedUnder(ActionCard actionCard);

}
