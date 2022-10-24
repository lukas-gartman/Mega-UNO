package org.megauno.app.model.game.utilities;

import org.junit.Test;
import org.megauno.app.model.cards.CardType;
import org.megauno.app.model.cards.ICard;
import org.megauno.app.model.cards.implementation.CardValidation;
import org.megauno.app.model.cards.implementation.NumberCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DeckTest {

    public Deck deck = new Deck();

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

    /*
    Tries to check if all cards in the deck are valid cards
    the loop iterates 50000 times to ensure that all the cards
    in the deck are tested. This is neccessary since "drawCard()" returns
    a random card. If more than 100 cards are generated for the deck, the amount of
    iterations should be increased.
    */
    @Test
    public void testAllCardsInDeck() {
       int relativelyLargeNum = 50000;
        for (int i = 0; i < relativelyLargeNum; i++) {
           assert(cardInvariant(deck.drawCard()));
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