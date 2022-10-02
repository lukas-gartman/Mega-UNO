package org.megauno.app.model.Cards;

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

    public static Color getFromIndex(int index) {
        for (Color ct : Color.values()) {
            if (ct.getIndex() == index)
                return ct;
        }
        throw new IllegalArgumentException();
    }
}
