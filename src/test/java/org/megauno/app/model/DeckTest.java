package org.megauno.app.model;

import org.junit.Test;
import org.megauno.app.model.Cards.CardType;
import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Cards.Impl.CardValidation;
import org.megauno.app.model.Cards.Impl.NumberCard;
import org.megauno.app.model.Game.Utilities.Deck;

import java.util.HashMap;
import java.util.List;

public class DeckTest {

    Deck deck = new Deck();

    HashMap<CardType, Integer> dist = new HashMap<>();

    Deck deckWithDist = new Deck();

    // throws an exception if the invariant doesn't hold
    public boolean cardInvariant(ICard card) {
        if (card instanceof NumberCard nc) {
            CardValidation.validateColorNc(nc.getColor());
            CardValidation.validateNumber(nc.getValue());
            CardValidation.validateType(nc.getType());
        }
        else {
            CardValidation.validateColor(card.getColor());
            CardValidation.validateType(card.getType());
        }
        return true;
    }

    @Test
    public void testDeckWithDistribution() {
        HashMap<CardType, Integer> dist = new HashMap<>();

        Deck deckWithDist = new Deck();
    }

    @Test
    public void testAllCardsInDeck() {
       for (ICard card : deck.getDeck()) {
           //System.out.println(card.toString());
           //System.out.println(card.getColor());
           assert(cardInvariant(card));
       }
    }

    @Test
    public void testDealHand() {
        List<ICard> hand = deck.dealHand(7);
        for (ICard card : hand) {
            //System.out.println(card.toString());
            assert(cardInvariant(card));
        }
        //System.out.println(hand.size());
        assert(hand.size() == 7);
    }

}