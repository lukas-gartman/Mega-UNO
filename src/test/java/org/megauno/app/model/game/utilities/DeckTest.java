package org.megauno.app.model.game.utilities;

import org.junit.Test;
import org.megauno.app.model.cards.CardType;
import org.megauno.app.model.cards.ICard;
import org.megauno.app.model.cards.implementation.CardValidation;
import org.megauno.app.model.cards.implementation.NumberCard;
import org.megauno.app.model.game.utilities.Deck;
import org.megauno.app.utility.BiHashMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DeckTest {

    Deck deck = new Deck();

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
        dist.put(CardType.NUMBERCARD, 50);
        dist.put(CardType.TAKETWO, 10);
        dist.put(CardType.REVERSECARD, 25);
        dist.put(CardType.WILDCARD, 15);
        Deck deckWithDist = new Deck(dist);
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

    @Test
    public void testDeckNoDealSameCardTwice(){
        List<ICard> cards = new ArrayList<>();
        for(int i = 0; i < 200; i++){
            ICard newCard = deck.drawCard();
            for (ICard card:cards) {
                assert(!(card == newCard));
            }
            cards.add(newCard);
        }
    }
    @Test
    public void testDeckNoDealSameCardTwiceWithDrawHand(){
        List<ICard> cards = new ArrayList<>();
        for(int i = 0; i < 200; i++){
            ICard newCard = deck.dealHand(1).get(0);
            for (ICard card:cards) {
                assert(!(card == newCard));
            }
            cards.add(newCard);
        }
    }



}