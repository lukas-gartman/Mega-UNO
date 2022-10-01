package org.megauno.app.model.Cards;

import org.megauno.app.model.Cards.Impl.ActionCard;
import org.megauno.app.model.Cards.Impl.NumberCard;

public interface ICard extends ICardVisitor {

    boolean equals(Object o);

    int hashCode();

    String toString();

    boolean canBePlayed(ICard c); // This is the "accept" method

    boolean canBePlayedOnMe(NumberCard nc);

    boolean canBePlayedOnMe(ActionCard ac);

    CardType getType();

    Color getColor();

    ICard copyCard();

	Integer getNumber();

    boolean activate();

    void setColor(Color choice);

    boolean canBeStacked(ICard c);

}
