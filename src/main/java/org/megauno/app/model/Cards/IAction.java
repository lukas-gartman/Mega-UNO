package org.megauno.app.model.Cards;

import org.megauno.app.model.Game.IActOnGame;

public interface IAction {

    boolean execute(IActOnGame g);
}
