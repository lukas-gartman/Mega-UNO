package org.megauno.app.model.cards;

import java.util.stream.Stream;

/**
 * The different types of cards in this representation of UNO.
 * Every specific card can be represented under a more overarching type, namely either
 * "Standard" or "ActionCard".
 */
public enum CardType {
    NUMBERCARD("Standard"),
    REVERSECARD("ActionCard"),
    WILDCARD("ActionCard"),
    TAKETWO("ActionCard"),
    TAKEFOUR("ActionCard"),
    ;
    private final String typeOf;

    CardType(String typeOf) {
        this.typeOf = typeOf;
    }

    public String getTypeOf() {
        return typeOf;
    }


    /**
     * Streams the different card types.
     *
     * @return A stream of the card types.
     */
    public static Stream<CardType> stream() {
        return Stream.of(CardType.values());
    }
}