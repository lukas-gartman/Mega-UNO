package org.megauno.app.model;

public interface IPile {
    void discard(ICard card);
    ICard getTop();
}
