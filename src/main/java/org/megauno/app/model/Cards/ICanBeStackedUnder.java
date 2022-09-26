package org.megauno.app.model.Cards;

import org.megauno.app.model.Cards.Impl.ActionCard;
import org.megauno.app.model.Cards.Impl.NumberCard;


//Visitor pattern
public interface ICanBeStackedUnder {

    boolean canBeStackedUnder(ActionCard ac);

    boolean canBeStackedUnder(NumberCard nc);

}

