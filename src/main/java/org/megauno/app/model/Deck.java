package org.megauno.app.model;

import org.megauno.app.model.Cards.CardType;
import org.megauno.app.model.Cards.Color;
import org.megauno.app.model.Cards.ICard;

import java.util.*;

public class Deck implements IDeck {
    private final Stack<ICard> cards = new Stack<>();
    private final CardFactory cardFactory = new CardFactory();
    private List<ICard> deck;
    private final HashMap<CardType, Integer> probabilities;
    public Deck() {
        // default distribution
        probabilities = generateFixedProbabilities();
        deck = generateDeck();
        //distribution.put(NumberCard, 66); // 66% chance for a NumberCard
        //distribution.put(ActionCard, 33); // 33% chance for an ActionCard
    }

    public Deck(HashMap dist) {
        this.probabilities = dist;
    }

    // For now, it is static probabilites, but this should be modified such that
    // it can take any probability and generate accordingly.
    private HashMap<CardType, Integer> generateFixedProbabilities() {
       HashMap<CardType, Integer> probabilites = new HashMap<>();
       //int distibution = (int) (CardType.stream().count() - 1);
       CardType.stream().forEach(c -> {
           if (c.getTypeOf().equals("Standard")) probabilites.put(c, 66);
           else probabilites.put(c, 11);
       });
       return probabilites;
    }

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
