package org.megauno.app.model.Cards;

public class CardUtility {
    public static boolean canBePlaced(NumberCard currentCard, NumberCard topCard) {
        return topCard.getValue() == currentCard.getValue() || topCard.getColor() == currentCard.getColor();
    }

    public static boolean canBePlaced(NumberCard currentCard, ActionCard topCard) {
       return currentCard.getColor() == topCard.getColor();
    }
}
