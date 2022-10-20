package org.megauno.app.model;

import junit.framework.TestCase;
import org.megauno.app.model.Cards.CardFactory;
import org.megauno.app.model.Cards.CardType;
import org.megauno.app.model.Cards.Color;
import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Cards.Impl.ActionCard;
import org.megauno.app.model.Cards.Impl.NumberCard;
import org.megauno.app.model.Game.Actions.TakeTwoAction;
import org.megauno.app.model.Game.Actions.WildCardAction;

public class CardFactoryTest extends TestCase {

    CardFactory cardFactory = new CardFactory();

    public void testCreateNumberCard() {
        ICard thisCard = new NumberCard(Color.BLUE, 4);
        ICard thatCard = cardFactory.createNumberCard(Color.BLUE, 4);
        assert(thisCard.equals(thatCard));
    }

    public void testCreatePureWildCard() {
        ICard thisCard = new ActionCard(new WildCardAction(), Color.GREEN, CardType.WILDCARD);
        ICard thatCard = cardFactory.createPureWildCard();
        assert(thisCard.equals(thatCard));
    }

    public void testCreateTakeTwoCard() {
        ICard thisCard = new ActionCard(new TakeTwoAction(), Color.RED, CardType.TAKETWO);
        ICard thatCard = cardFactory.createActionCard(Color.RED, CardType.TAKETWO);
        assert(thisCard.equals(thatCard));
    }
}