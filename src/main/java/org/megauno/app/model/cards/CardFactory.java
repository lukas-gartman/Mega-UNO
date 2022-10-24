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

    /**
     * Creates a number card.
     * @param color The color of the number card to be created.
     * @param value The numeric value of the card to be created, should be (0-9).
     * @return A number card, if given valid arguments.
     */
    public ICard createNumberCard(Color color, int value) {
        return new NumberCard(color, value);
    }

    /**
     * Creates a wildcard. No parameters needed, since wildcard doesn't initially have a color
     * @return  A wildcard.
     */
    public ICard createPureWildCard() {
        return new ActionCard(new WildCardAction(), Color.NONE, CardType.WILDCARD);
    }

    /**
     * Creates a action card.
     * @param color The color of the action card to be created.
     * @param type The specific type of action card.
     * @return A action card with the action of the given type.
     */
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
