package org.megauno.app.model;

import org.megauno.app.model.Cards.CardType;
import org.megauno.app.model.Cards.Color;
import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Cards.Impl.ActionCard;
import org.megauno.app.model.Cards.Impl.NumberCard;
import org.megauno.app.model.Game.Actions.ReverseAction;
import org.megauno.app.model.Game.Actions.TakeFourAction;
import org.megauno.app.model.Game.Actions.TakeTwoAction;
import org.megauno.app.model.Game.Actions.WildCardAction;
import org.megauno.app.model.Game.Game;
import org.megauno.app.model.Game.PlayerCircle;

import java.lang.reflect.WildcardType;

public class CardFactory {



    public ICard createNumberCard(Color color, int value) {
        return new NumberCard(color, value);
    }

    public ICard createPureWildCard(){
        return new ActionCard(new WildCardAction(), Color.NONE, CardType.WILDCARD);
    }

    public ICard createActionCard(Color color, CardType type, Game game) {
        return
        switch (type) {
            case NUMBERCARD -> null;
            case REVERSECARD -> new ActionCard(new ReverseAction(game.getPlayers()), color, type);
            case WILDCARD -> new ActionCard(new TakeFourAction(game.getDeck(), game.getPlayers()), color, type);
            case TAKETWO -> new ActionCard(new TakeTwoAction(game.getDeck(), game.getPlayers()), color, type);
        };
    }
}
