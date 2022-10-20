package org.megauno.app.model.Game;

import org.junit.Before;
import org.junit.Test;
import org.megauno.app.model.Cards.CardFactory;
import org.megauno.app.model.Cards.CardType;
import org.megauno.app.model.Cards.Color;
import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Game.Utilities.PlayerCircle;
import org.megauno.app.model.GeneralTestMethods;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ActionTest {

    CardFactory cardFactory;
    List<ICard> actionCards;
    Random random;
    Game testGame;
    PlayerCircle playerCircle;

    @Before
    public void setUp() {
        cardFactory = new CardFactory();
        actionCards = new ArrayList<>();
        playerCircle = GeneralTestMethods.generatePlayerCircle(5);
        random = new Random();
        testGame = new Game(playerCircle);
        testGame.start(5);
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
