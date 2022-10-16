package org.megauno.app.model.Player;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.megauno.app.model.Cards.Color;
import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Cards.Impl.NumberCard;

import java.util.ArrayList;
import java.util.List;


public class PlayerTest extends TestCase {
    private Player emptyPlayer;
    private Player cardsPlayer;
    private List<ICard> cards = new ArrayList<>();
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        cards.add(new NumberCard(Color.GREEN,5));
        cards.add(new NumberCard(Color.BLUE,5));
        cards.add(new NumberCard(Color.GREEN,8));
    }
    @Before
    public void makePlayer(){
        emptyPlayer = new Player();
        cardsPlayer = new Player();

        for (ICard c:cards) {
            cardsPlayer.addCard(c);
            cardsPlayer.selectCard(c);
        }
    }

    @Test
    public void testUnSelectedAllCardsWithNoCards() {
        emptyPlayer.discardAllSelectedCards();
        assertTrue(emptyPlayer.play().size() == 0);
    }

    @Test
    public void testUnSelectAllWithCardsSomeCards() {
        cardsPlayer.discardAllSelectedCards();
        assertTrue(cardsPlayer.play().size() == 0);
    }

    @Test
    public void testSelectingCardWithCardSomeCards() {
        for (ICard c : cards) {
            emptyPlayer.addCard(c);
            emptyPlayer.selectCard(c);
        }
        assertTrue(emptyPlayer.play().containsAll(cards));
    }
}
