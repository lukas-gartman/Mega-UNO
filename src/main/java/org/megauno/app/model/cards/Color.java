package org.megauno.app.model.cards;

/**
 * Defines the colors in this UNO domain. Each color has an index, such that they can more
 * precisely be enumerated.
 */
public enum Color {
    BLUE(0),
    GREEN(1),
    RED(2),
    YELLOW(3),
    NONE(4);

    private final int index;

    Color(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    /**
     * @param index the index of which a particular color maps to.
     * @return The card at the given index.
     */
    public static Color getFromIndex(int index) {
        for (Color ct : Color.values()) {
            if (ct.getIndex() == index)
                return ct;
        }
        throw new IllegalArgumentException("Index out of bounds");
    }
}
