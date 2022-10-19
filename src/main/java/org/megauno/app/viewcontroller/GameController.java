package org.megauno.app.viewcontroller;

import org.megauno.app.model.Cards.Color;
import org.megauno.app.model.Cards.ICard;


//When these methodes are triggered
public interface GameController {
    void selectCard(int cardId);

    void unSelectCard(int cardId);

    void commenceForth();

    void sayUno();

    void setColor(Color color);

    void drawCard();
}
