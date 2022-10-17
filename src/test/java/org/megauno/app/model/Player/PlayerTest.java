package org.megauno.app.model.Player;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.megauno.app.model.Cards.Color;
import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Cards.Impl.ActionCard;
import org.megauno.app.model.Cards.Impl.NumberCard;

import java.util.ArrayList;
import java.util.List;


public class PlayerTest {
    Player empltyPlayer;
    Player cardsPlayer;
    List<ICard> cards = new ArrayList<>();
    //setUp();
    //makePlayer();
    @Before
    public void setUp() throws Exception {
        cards.add(new NumberCard(Color.GREEN,5));
        cards.add(new NumberCard(Color.BLUE,5));
        cards.add(new NumberCard(Color.GREEN,8));


    }
    @Before
    public void makePlayer() throws Exception {
        empltyPlayer = new Player();
        cardsPlayer = new Player();
        setUp();

        for (ICard c:cards) {
            cardsPlayer.addCard(c);
            cardsPlayer.selectCard(c);
        }
    }

    @Test
    public void testUnSelectedAllCardsWithNoCards(){
        empltyPlayer.discardAllSelectedCards();
        Assert.assertTrue(empltyPlayer.play().size() == 0);
    }
    @Test
    public void testUnSelectAllWithCardsSomeCards(){
        cardsPlayer.discardAllSelectedCards();
        Assert.assertTrue(cardsPlayer.play().size() == 0);
    }


    @Test
    public void testSelectingCardWithCardSomeCards(){
        for (ICard c:cards) {
            empltyPlayer.addCard(c);
            empltyPlayer.selectCard(c);
        }
        Assert.assertTrue(empltyPlayer.play().containsAll(cards));
    }


}
