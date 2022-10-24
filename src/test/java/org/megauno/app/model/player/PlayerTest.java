package org.megauno.app.model.player;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.megauno.app.model.cards.Color;
import org.megauno.app.model.cards.ICard;
import org.megauno.app.model.cards.implementation.NumberCard;
import org.megauno.app.utility.Publisher.ISubscribable;
import org.megauno.app.utility.Publisher.normal.Publisher;
import org.megauno.app.utility.Tuple;

import java.util.ArrayList;
import java.util.List;


public class PlayerTest {
    public Player emptyPlayer;
    public Player  player;
    public List<ICard> cards = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        cards.add(new NumberCard(Color.GREEN,5));
        cards.add(new NumberCard(Color.GREEN,5));
        cards.add(new NumberCard(Color.BLUE,5));
        cards.add(new NumberCard(Color.GREEN,8));
        emptyPlayer = new Player();
        player = new Player();
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
        Player playerWithCards = new Player(cards);
        assert(playerWithCards.numOfCards() == cards.size());
    }

    @Test
    public void testUno() {
        player.sayUno();
        assert(player.uno());
    }

    @Test
    public void testPublishers() {
        ISubscribable<Tuple<Player, List<ICard>>> expected = new Publisher<>();
        assert(player.getOnCardsAddedByPlayer().equals(expected));
        assert(player.getOnCardRemovedByPlayer().equals(expected));
    }

    @Test
    public void testRemoveSelectedCardsFromHand() {
        int handSize = player.numOfCards();
        ICard firstCard = player.getCards().get(0);
        player.selectCard(firstCard);
        player.removeSelectedCardsFromHand();
        assert(player.numOfCards() == handSize - 1);
    }

    @Test
    public void testUnsayUno() {
        player.sayUno();
        player.unsayUno();
        Assert.assertFalse(player.uno());
    }

}
