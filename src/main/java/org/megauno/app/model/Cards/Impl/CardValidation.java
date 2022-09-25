package org.megauno.app.model.Cards.Impl;

import org.megauno.app.model.Cards.CardType;
import org.megauno.app.model.Cards.Color;

/**
 * To ensure that the cards created are valid.
 */
public class CardValidation {

    public static void validateColor(Color color) {
        if (color == null || !checkColor(color))
            throw new IllegalArgumentException("Undefined color");
    }

    public static void validateColorNc(Color color) {
		// This kills my compiler, sometimes
        //if (color == null || color == Color.NONE || !checkColor(color))
        //    throw new IllegalArgumentException("Undefined or wrong color");
    }

    private static boolean checkColor(Color color) {
        for (Color c: Color.values()) {
            if (color == c) return true;
        }
        return false;
    }

    public static void validateNumber(int num) {
        if (num <= 0 || num > 9)
            throw new IllegalArgumentException("Number out of range");
    }

    private static boolean checkType(CardType type) {
        for (CardType ct: CardType.values()) {
            if (type == ct) return true;
        }
        return false;
    }

    public static void validateType(CardType type) {
        if (type == null || !checkType(type))
            throw new IllegalArgumentException("Undefined card type");
    }

    // public static void validSpecialCard();


}
