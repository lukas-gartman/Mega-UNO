package Cards;

import java.util.Objects;

public class NumberCard extends AbstractCard {
    private final int value;

    public NumberCard(Color color, int value) {
        super(color, CardType.NUMBERCARD);
        CardValidation.validateColor(color);
        CardValidation.validateNumber(value);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getColor(), value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumberCard that = (NumberCard) o;
        return value == that.value && this.getColor() == that.getColor();
    }

    @Override
    public String toString() {
        return "NumberCard{" +
                "color=" + super.getColor() +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean canBePlayed(Card c) {
        return false;
    }
}
