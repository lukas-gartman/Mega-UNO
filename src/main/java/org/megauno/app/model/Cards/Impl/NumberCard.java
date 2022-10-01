package org.megauno.app.model.Cards.Impl;

import org.megauno.app.model.Cards.*;

import java.util.Objects;

public class NumberCard extends AbstractCard {
    private final int value;

    public NumberCard(Color color, int value) {
        super(color, CardType.NUMBERCARD);
        CardValidation.validateColorNc(color);
        CardValidation.validateNumber(value);
        this.value = value;
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
                ", value=" + value +
                '}';
    }

    // what to do with NONE type?
    // Why it isn't a good idea to use visitor interface is because Card is both the
    // acceptor and the visitor, which means that they cannot be under the same interface
    // and at the same time be under separate interfaces (Visitor, accepter (element))
    @Override
    public boolean canBePlayed(ICard c) {
        return c.visit(this);
        //return c.canBePlayedOnMe(this);
    }

    @Override
    public boolean canBePlayedOnMe(NumberCard c) {
        return this.getColor() == c.getColor() || this.value == c.getValue();
    }

    @Override
    public boolean canBePlayedOnMe(ActionCard c) {
        return this.getColor() == c.getColor();
    }

    /*@Override
    public boolean canBePlayed(NumberCard nc) {
        return this.getColor() == nc.getColor() || this.value == nc.getValue();
    }*/

    // Shallow copy, no need for deep copy since the attributes are immutable.
    @Override
    public ICard copyCard() {
        return new NumberCard(this.getColor(), this.getValue());
    }

    // This is equivalent to canBePlayedOnMe
    @Override
    public boolean visit(ActionCard ac) {
        return this.getColor() == ac.getColor();
    }

    @Override
    public boolean visit(NumberCard nc) {
        return this.getColor() == nc.getColor() || this.value == nc.getValue();
    }

    @Override
    public boolean activate() {
        return true;
    }

	@Override
	public Integer getNumber() {
		return value;
	}
}
