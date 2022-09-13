package Cards;

/**
 * To ensure that the cards created are valid.
 */
public class CardValidation {

    public static void validateColor(Color color) {
        if (color == null || !checkColor(color)) {
            throw new IllegalArgumentException("Undefined color");
        }
    }

    private static boolean checkColor(Color color) {
        for (Color c: Color.values()) {
            if (color == c) return true;
        }
        return false;
    }

    public static void validateNumber(int num) {
        if (num <= 0 || num > 9) {
            throw new IllegalArgumentException("Number out of range");
        }
    }

    // public static void validSpecialCard();


}
