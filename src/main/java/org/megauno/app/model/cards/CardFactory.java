package org.megauno.app.model.cards;

import org.megauno.app.model.cards.implementation.ActionCard;
import org.megauno.app.model.cards.implementation.NumberCard;
import org.megauno.app.model.game.actions.ReverseAction;
import org.megauno.app.model.game.actions.TakeFourAction;
import org.megauno.app.model.game.actions.TakeTwoAction;
import org.megauno.app.model.game.actions.WildCardAction;

/**
 * Handles all the details of creating cards. Here is where the action cards gets its action that it can execute.
 */
public class CardFactory {

    public ICard createNumberCard(Color color, int value) {
        return new NumberCard(color, value);
    }

    public ICard createPureWildCard() {
        return new ActionCard(new WildCardAction(), Color.NONE, CardType.WILDCARD);
    }

    public ICard createActionCard(Color color, CardType type) {
        return switch (type) {
            case NUMBERCARD -> null;
            case REVERSECARD -> new ActionCard(new ReverseAction(), color, type);
            case TAKETWO -> new ActionCard(new TakeTwoAction(), color, type);
            case TAKEFOUR -> new ActionCard(new TakeFourAction(), color, type);
            case WILDCARD -> new ActionCard(new WildCardAction(), color, type);
        };
    }
}
