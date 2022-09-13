package Cards;

import java.util.Objects;

public class NumberCard implements Card {
    private final Color color;
    private final int value;

    public NumberCard(Color color, int value) {
        CardValidation.validateColor(color);
        CardValidation.validateNumber(value);
        this.color = color;
        this.value = value;
    }

    public Color getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumberCard that = (NumberCard) o;
        return value == that.value && color == that.color;
    }

    @Override
    public String toString() {
        return "NumberCard{" +
                "color=" + color +
                ", value=" + value +
                '}';
    }
}
