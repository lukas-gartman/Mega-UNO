package org.megauno.app.model.cards;

import junit.framework.TestCase;
import org.junit.Assert;
import org.megauno.app.model.cards.CardFactory;
import org.megauno.app.model.cards.CardType;
import org.megauno.app.model.cards.Color;
import org.megauno.app.model.cards.ICard;
import org.megauno.app.model.cards.implementation.ActionCard;
import org.megauno.app.model.cards.implementation.NumberCard;
import org.megauno.app.model.game.actions.TakeTwoAction;
import org.megauno.app.model.game.actions.WildCardAction;

public class CardFactoryTest extends TestCase {

    CardFactory cardFactory = new CardFactory();

    public void testCreateNumberCard() {
        ICard thisCard = new NumberCard(Color.BLUE, 4);
        ICard thatCard = cardFactory.createNumberCard(Color.BLUE, 4);
        assert(thisCard.equals(thatCard));
    }

    public void testCreatePureWildCard() {
        ICard thisCard = new ActionCard(new WildCardAction(), Color.NONE, CardType.WILDCARD);
        ICard thatCard = cardFactory.createPureWildCard();
        assert(thisCard.equals(thatCard));
    }

    public void testCreateTakeTwoCard() {
        ICard thisCard = new ActionCard(new TakeTwoAction(), Color.RED, CardType.TAKETWO);
        ICard thatCard = cardFactory.createActionCard(Color.RED, CardType.TAKETWO);
        assert(thisCard.equals(thatCard));
    }

    public void testCreateWrong() {
        Assert.assertNull(cardFactory.createActionCard(Color.RED, CardType.NUMBERCARD));
    }
}