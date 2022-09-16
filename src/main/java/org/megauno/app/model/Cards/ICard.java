package org.megauno.app.model.Cards;

public interface ICard {

    boolean equals(Object o);

    int hashCode();

    String toString();

    boolean canBePlayed(ICard c);

    CardType getType();

    Color getColor();

    ICard copyCard();
}
