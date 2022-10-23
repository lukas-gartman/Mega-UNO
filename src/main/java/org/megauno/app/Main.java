package org.megauno.app;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.megauno.app.application.ClientApplication;
import org.megauno.app.application.ModelApplication;
import org.megauno.app.viewcontroller.lobby.LobbyApplication;

import java.util.Scanner;

public class Main {
//    public static void main(String[] args) {
//        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
//        System.out.println("Type s for server and just enter for client");
//        String answer = new Scanner(System.in).nextLine();
//        ApplicationAdapter app = new ClientApplication();
//        if (answer.equals("s")) {
//            app = new ModelApplication();
//        }
//        new Lwjgl3Application(app, config);
//    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Mega UNO");
        config.setWindowedMode(1600, 900);
//        config.setWindowSizeLimits(1600, 900, 1600, 900);
        ApplicationAdapter app = new LobbyApplication();
        new Lwjgl3Application(app, config);
    }

}
