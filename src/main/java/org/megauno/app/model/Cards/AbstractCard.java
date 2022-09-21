package org.megauno.app.model.Cards;

public abstract class AbstractCard implements ICard {

    private final Color color;
    private final CardType type;

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
    public abstract boolean canBePlayed(ICard c);

    @Override
    public abstract boolean canBePlayedOnMe(NumberCard c);

    @Override
    public abstract boolean canBePlayedOnMe(ActionCard ac);

    public abstract ICard copyCard();
}
