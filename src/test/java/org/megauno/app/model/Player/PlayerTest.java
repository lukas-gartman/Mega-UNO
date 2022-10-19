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
    Player emptyPlayer;
    Player  player;
    List<ICard> cards = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        cards.add(new NumberCard(Color.GREEN,5));
        cards.add(new NumberCard(Color.BLUE,5));
        cards.add(new NumberCard(Color.GREEN,8));
        emptyPlayer = new Player(10);
        player = new Player(20);
        player.addCards(cards);
    }

    @Test
    public void testUnSelectedAllCardsWithNoCards(){
        emptyPlayer.discardAllSelectedCards();
        assert(emptyPlayer.play().size() == 0);
    }

    @Test
    public void testUnSelectAllWithCardsSomeCards(){
        player.discardAllSelectedCards();
        assert(player.play().size() == 0);
    }


    @Test
    public void testSelectingCardWithCardSomeCards(){
        for (ICard c:cards) {
            emptyPlayer.addCard(c);
            emptyPlayer.selectCard(c);
        }
        assert(emptyPlayer.play().containsAll(cards));
    }

    @Test
    public void testUnSelectCard() {
        for (ICard c : cards) {
            player.selectCard(c);
        }

        int selectedCardsBefore = player.getSelectedCards().size();
        player.unSelectCard(cards.get(0));
        assert(player.getSelectedCards().size() == (selectedCardsBefore - 1));
    }

    @Test
    public void testGetCards() {
        List<ICard> collectedCards = player.getCards();
        for (int i = 0; i < collectedCards.size(); i++) {
            assert(collectedCards.get(i).equals(cards.get(i)));
        }
        assert(collectedCards.size() == cards.size());
        //assert(collectedCards.size() == cardsPlayer.numOfCards());
    }

    @Test
    public void testConstructor() {
        Player playerWithCards = new Player(33, cards);
        assert(playerWithCards.numOfCards() == cards.size());
    }

    @Test
    public void testUno() {
        player.sayUno();
        assert(player.uno());
    }

}
