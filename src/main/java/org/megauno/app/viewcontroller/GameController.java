package org.megauno.app.viewcontroller;

import org.megauno.app.model.Cards.ICard;

public interface GameController {
    void selectCardofPlayer(ICard card, int id);

    void unSelectCardofPlayer(ICard card, int id);
}
