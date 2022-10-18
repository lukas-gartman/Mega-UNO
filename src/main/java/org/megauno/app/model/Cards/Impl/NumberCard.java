package org.megauno.app.model.Cards.Impl;

import org.megauno.app.model.Cards.*;
import org.megauno.app.model.Game.IActOnGame;

import java.util.Objects;

/**
 *
 */
public class NumberCard extends AbstractCard {
    private final int value;

    /**
     * The only constructor for NumberCard, has default card type NUMBERCARD.
     * The constructor validates the card, i.e. checks that the given values are
     * correct.
     * @param color Color of the card.
     * @param value Numeric value of the card.
     */
    public NumberCard(Color color, int value) {
        super(color, CardType.NUMBERCARD);
        CardValidation.validateColorNc(color);
        CardValidation.validateNumber(value);
        this.value = value;
    }

    @Override
    public Integer getNumber() {
        return value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getColor(), value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumberCard that = (NumberCard) o;
        return value == that.value && this.getColor() == that.getColor();
    }

    @Override
    public String toString() {
        return "NumberCard{" +
                "color=" + super.getColor() +
                ", type=" + super.getType() +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean canBePlayed(ICard c) {
        return c.canBePlayedOnMe(this);
    }

    @Override
    public boolean canBePlayedOnMe(NumberCard numberCard) {
        return this.getColor() == numberCard.getColor() || this.value == numberCard.getValue();
    }

    @Override
    public boolean canBePlayedOnMe(ActionCard actionCard) {
        return this.getColor() == actionCard.getColor();
    }

    /**
     * Shallow of a card, no need for deep copy since the attributes are immutable.
     * @return A copy of this card.
     */
    @Override
    public ICard copyCard() {
        return new NumberCard(this.getColor(), this.getValue());
    }

    @Override
    public boolean activate(IActOnGame g) {
        return true;
    }


    @Override
    public boolean canBeStacked(ICard c) {
        return c.canBeStackedUnder(this);
    }

    @Override
    public boolean canBeStackedUnder(ActionCard ac) {
        return false;
    }

    @Override
    public boolean canBeStackedUnder(NumberCard nc) {
        return nc.getValue() == this.getValue();
    }
    

}
