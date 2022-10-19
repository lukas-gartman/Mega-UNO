package org.megauno.app.model.Game;

import org.junit.Before;
import org.junit.Test;
import org.megauno.app.model.CardFactory;
import org.megauno.app.model.Cards.CardType;
import org.megauno.app.model.Cards.Color;
import org.megauno.app.model.Cards.ICard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ActionTest {

    CardFactory cardFactory;
    List<ICard> actionCards;
    Random random;
    Game testGame;

    @Before
    public void setUp() {
        cardFactory = new CardFactory();
        actionCards = new ArrayList<>();
        random = new Random();
        testGame = new Game();
        CardType.stream().filter(t -> t.getTypeOf().equals("ActionCard"))
                .forEach(type -> actionCards.add(
                    cardFactory.createActionCard(
                            Color.getFromIndex(random.nextInt(3)),
                            type)
        ));
        System.out.println(actionCards.toString());
    }

    @Test
    public void testActions() {
       for (ICard card : actionCards) {
           card.activate(testGame);
       }
    }
}
