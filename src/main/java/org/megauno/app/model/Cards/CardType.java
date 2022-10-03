package org.megauno.app.model.Cards;

import java.util.stream.Stream;

public enum CardType {
    NUMBERCARD("Standard"),
    REVERSECARD("ActionCard"),
    WILDCARD("ActionCard"),
    TAKETWO("ActionCard"),
    ;
    private final String typeOf;
    CardType(String typeOf) {
       this.typeOf = typeOf;
    }

    public String getTypeOf() {
        return typeOf;
    }


    public static Stream<CardType> stream() {
        return Stream.of(CardType.values());
    }
}