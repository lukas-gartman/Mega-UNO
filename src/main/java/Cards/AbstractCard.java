package Cards;

public abstract class AbstractCard implements Card {

    private final Color color;
    private final CardType type;

    protected AbstractCard(Color color, CardType type) {
        this.color = color;
        this.type = type;
    }

    public CardType getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public abstract boolean canBePlayed(Card c);
}
