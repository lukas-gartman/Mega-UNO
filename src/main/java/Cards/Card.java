package Cards;

public interface Card {

    public boolean equals(Object o);

    public int hashCode();

    public String toString();

    public boolean canBePlayed(Card c);

    public CardType getType();

    public Color getColor();
}
