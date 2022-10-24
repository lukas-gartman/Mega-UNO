package org.megauno.app.model.cards;

import org.megauno.app.model.cards.implementation.ActionCard;
import org.megauno.app.model.cards.implementation.NumberCard;
import org.megauno.app.model.game.IActOnGame;

/**
 * The common methods that every type of card extends.
 */
public abstract class AbstractCard implements ICard {

    private Color color;
    private final CardType type;

    /**
     * Every card has a color and a type upon creation.
     *
     * @param color The specified color of the card.
     * @param type  The specified type of the card.
     */
    protected AbstractCard(Color color, CardType type) {
        this.color = color;
        this.type = type;
    }

    @Override
    public CardType getType() {
        return type;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public Integer getNumber() {
        return null;
    }

    /**
     * Checks that this card can be played on the given card.
     *
     * @param card The card that is played on.
     * @return If the card can be played or not.
     */
    @Override
    public abstract boolean canBePlayed(ICard card);

    /**
     * Visitor Pattern. Checks that the given card can be played on this card.
     *
     * @param numberCard The card that wants to be played on this card
     * @return Weather it can be played on this or not.
     */
    @Override
    public abstract boolean canBePlayedOnMe(NumberCard numberCard);

    /**
     * Visitor Pattern. Checks that the given card can be played on this card.
     *
     * @param actionCard The card that wants to be played on this card
     * @return Weather it can be played on this or not.
     */
    @Override
    public abstract boolean canBePlayedOnMe(ActionCard actionCard);

    public abstract ICard copyCard();

    /**
     * Propagates the changes a particular card has on the game.
     *
     * @param g An abstraction of a game.
     * @return Weather the change was successfully done.
     */
    @Override
    public abstract boolean activate(IActOnGame g);

    /**
     * Sets the color of the card
     * @param c the color to be set.
     */
    public void setColor(Color c) {
        this.color = c;

    }
}
