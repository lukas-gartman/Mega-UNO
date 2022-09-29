package org.megauno.app.viewcontroller;

import org.megauno.app.model.Player.Player;

public interface IGame {
    Player getCurrent();
    Player getPlayerWithId(int id);
    Player[] getPlayers();

}
