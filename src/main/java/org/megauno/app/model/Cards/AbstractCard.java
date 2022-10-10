package org.megauno.app.model.Cards;

import org.megauno.app.model.Cards.Impl.ActionCard;
import org.megauno.app.model.Cards.Impl.NumberCard;
import org.megauno.app.model.Game.IActOnGame;

public abstract class AbstractCard implements ICard {

    private Color color;
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
	public Integer getNumber() {
		return null;
	}

    @Override
    public abstract boolean canBePlayed(ICard c);

    @Override
    public abstract boolean canBePlayedOnMe(NumberCard c);

    @Override
    public abstract boolean canBePlayedOnMe(ActionCard ac);

    public abstract ICard copyCard();

    @Override
    public abstract boolean visit(ActionCard ac);

    @Override
    public abstract boolean visit(NumberCard nc);

    @Override
    public abstract boolean activate(IActOnGame g);

    public void setColor(Color c){
        this.color = c;

    }
}
