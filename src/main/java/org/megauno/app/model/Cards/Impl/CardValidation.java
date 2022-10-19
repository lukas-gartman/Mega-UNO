package org.megauno.app.model.Cards.Impl;

import org.megauno.app.model.Cards.CardType;
import org.megauno.app.model.Cards.Color;

/**
 * To ensure that the cards created are valid.
 */
public class CardValidation {

    /**
     * Checks that the color given to a card is a valid color
     * @param color the color given from the card.
     */
    public static void validateColor(Color color) {
        if (!checkColor(color))
            throw new IllegalArgumentException("Undefined color");
    }

    /**
     * Checks that the color given to a number card is valid.
     * For instance, NONE is not valid.
     * @param color the color given from the card.
     */
    public static void validateColorNc(Color color) {
		// This kills my compiler, sometimes
        if (color == Color.NONE || !checkColor(color))
            throw new IllegalArgumentException("Undefined or wrong color");
    }

    private static boolean checkColor(Color color) {
        for (Color c: Color.values()) {
            if (color == c) return true;
        }
        return false;
    }

    /**
     * Checks that the numeric value of a number card isn't out of scope.
     * @param num the number of a number card.
     */
    public static void validateNumber(int num) {
        if (num < 0 || num > 9)
            throw new IllegalArgumentException("Number out of range");
    }

    private static boolean checkType(CardType type) {
        for (CardType ct: CardType.values()) {
            if (type == ct) return true;
        }
        return false;
    }

    /**
     * Checks that the type of a card is valid.
     * @param type the type given from a card.
     */
    public static void validateType(CardType type) {
        if (!checkType(type))
            throw new IllegalArgumentException("Undefined card type");
    }
}
