package org.megauno.app.model;

import org.megauno.app.model.Cards.ICard;

public interface IPile {
    void discard(ICard card);
    ICard getTop();
}
