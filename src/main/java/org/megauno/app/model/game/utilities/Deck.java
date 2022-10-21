package org.megauno.app.model.game.utilities;

import org.megauno.app.model.cards.CardFactory;
import org.megauno.app.model.cards.CardType;
import org.megauno.app.model.cards.Color;
import org.megauno.app.model.cards.ICard;
import org.megauno.app.model.game.IDeck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Deck is responsible for generating the cards, which it does upon creation, either with
 * a given distribution or a standard distribution. From this distribution of cards one
 * can draw a card, which in practice is done in game, for when a player wants or needs
 * to draw a card.
 */
public class Deck implements IDeck {
    private final CardFactory cardFactory = new CardFactory();
    private List<ICard> deck;
    private final HashMap<CardType, Integer> probabilities;

    /**
     * The constructor for deck which uses a fixed probability.
     */
    public Deck() {
        probabilities = generateFixedProbabilities();
        deck = generateDeck();
    }

    /**
     * The constructor for deck which uses the given distribution of cards to generate a deck
     * @param dist The distribution from which the cards are generated from.
     */
    public Deck(HashMap dist) {
        this.probabilities = dist;
    }

    /*
    For now, it is static probabilities, 66% for number cards and 33% for action cards
    but this method should be modified such that it can take any probability
    and generate the distribution of cards accordingly.
     */

    /**
     * For now, it is static probabilities, 66% for number cards and 33% for action cards
     * but this method should be modified such that it can take any probability
     * and generate the distribution of cards accordingly.
     * @return The fixed distribution of which cards to be created.
     */
    private HashMap<CardType, Integer> generateFixedProbabilities() {
        HashMap<CardType, Integer> probabilities = new HashMap<>();
        //int distibution = (int) (CardType.stream().count() - 1);
        CardType.stream().forEach(c -> {
            if (c.getTypeOf().equals("Standard")) probabilities.put(c, 66);
            else probabilities.put(c, 11);
        });
        return probabilities;
    }

    /*
    Generates the deck from a fixed distribution, with even amounts of colors
    for all cards (outside of bounds for the amount in the distribution)
    and equal amounts of numeric values for number card (with some difference
    since 66 % 9 != 0.
     */

    /**
     * Generates a deck, either from a fixed distribution or a given distribution,
     * depending on which constructor was used upon creation of the class.
     * @return The generated deck.
     */
    private List<ICard> generateDeck() {
        ArrayList<ICard> deck = new ArrayList<>();
        int numberIndex = 0;
        int colorIndex = 0;
        for (CardType ct : probabilities.keySet()) {
            if (ct == CardType.NUMBERCARD) {
                for (int i = 0; i < probabilities.get(ct); i++) {
                    deck.add(cardFactory.createNumberCard(Color.getFromIndex(colorIndex), numberIndex + 1));
                    colorIndex = (colorIndex + 1) % (Color.values().length - 1); // to ignore NONE
                    numberIndex = (numberIndex + 1) % 9; // such that no value larger than 9 is picked
                }
            } else {
                for (int i = 0; i < probabilities.get(ct); i++) {
                    deck.add(cardFactory.createActionCard(Color.getFromIndex(colorIndex), ct));
                    colorIndex = (colorIndex + 1) % (Color.values().length - 1); // to ignore NONE
                }
            }
        }
        return deck;
    }

    // For testing
    public List<ICard> getDeck() {
        return deck;
    }

    @Override
    public ICard drawCard() {
        Random rand = new Random();
        int index = rand.nextInt(deck.size());
        ICard randomCard = deck.get(index);
        return randomCard.copyCard();
    }

    @Override
    public List<ICard> dealHand(int sizeHand) {
        List<ICard> hand = new ArrayList<>();
        for (int i = 0; i < sizeHand; i++) {
            hand.add(drawCard());
        }
        return hand;
    }
}
