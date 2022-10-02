package org.megauno.app.model;

import org.megauno.app.model.Cards.CardType;
import org.megauno.app.model.Cards.Color;
import org.megauno.app.model.Cards.ICard;

import java.util.*;

public class Deck implements IDeck {
    private final CardFactory cardFactory = new CardFactory();
    private List<ICard> deck;
    private final HashMap<CardType, Integer> probabilities;
    public Deck() {
        probabilities = generateFixedProbabilities();
        deck = generateDeck();
    }

    public Deck(HashMap dist) {
        this.probabilities = dist;
    }

    /*
    For now, it is static probabilities, 66% for number cards and 33% for action cards
    but this method should be modified such that it can take any probability
    and generate the distribution of cards accordingly.
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
    private List<ICard> generateDeck() {
        ArrayList<ICard> deck = new ArrayList<>();
        int numberIndex = 0;
        int colorIndex = 0;
        for (CardType ct : probabilities.keySet()) {
            if (ct == CardType.NUMBERCARD) {
               for (int i = 0; i < probabilities.get(ct); i++) {
                   deck.add(cardFactory.createNumberCard(Color.getFromIndex(colorIndex), numberIndex));
                   colorIndex = (colorIndex + 1) % Color.values().length - 2; // to ignore NONE
                   numberIndex = (numberIndex + 1) % 9; // such that no value larger than 9 is picked
               }
            }
            else {
                for (int i = 0; i < probabilities.get(ct); i++) {
                    deck.add(cardFactory.createActionCard(Color.getFromIndex(colorIndex), ct));
                    colorIndex = (colorIndex + 1) % Color.values().length - 2; // to ignore NONE
                }
            }
        }
        return deck;
    }


    public ICard drawCard() {
        Random rand = new Random();
        int index = rand.nextInt(deck.size());
        ICard randomCard = deck.get(index);
        return randomCard;
    }
}
