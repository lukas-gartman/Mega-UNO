package org.megauno.app.model.Cards;

import org.megauno.app.model.Cards.Impl.ActionCard;
import org.megauno.app.model.Cards.Impl.NumberCard;

// Defines some object that wants to interact with cards in some way
public interface ICardVisitor {

    boolean visit(ActionCard ac);

    boolean visit(NumberCard nc);
}