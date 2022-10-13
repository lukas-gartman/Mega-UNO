package org.megauno.app;

import org.megauno.app.model.Deck;
import org.megauno.app.model.Game.Game;
import org.megauno.app.model.Game.PlayerCircle;
import org.megauno.app.model.Player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args ) {
        //Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        //config.setMaximized(true);
        //new Lwjgl3Application(new Application(), config);
        ViewlessGame game = new ViewlessGame();
        game.run();
    }

}