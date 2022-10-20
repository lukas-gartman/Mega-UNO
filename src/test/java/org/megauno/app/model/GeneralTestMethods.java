package org.megauno.app.model;

import org.megauno.app.model.Game.Utilities.PlayerCircle;
import org.megauno.app.model.Player.Player;

import java.util.ArrayList;
import java.util.List;

public class GeneralTestMethods {

    public static PlayerCircle generatePlayerCircle(int n) {
        List<Player> playerList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            playerList.add(new Player());
        }
        return new PlayerCircle(playerList);
    }
}
