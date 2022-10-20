package org.megauno.app.model.Player;

import java.util.List;

public interface IPlayer<c> {
    List<c> play();
    void addCard(c item);

    int numOfCards();

    boolean uno();

    List<c> getCards();

    void selectCard(c item);
    
    int getId();

}
