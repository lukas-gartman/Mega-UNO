package Cards;

public interface ICard {

    public boolean equals(Object o);

    public int hashCode();

    public String toString();

    public boolean canBePlayed(ICard c);

    public CardType getType();

    public Color getColor();
}
