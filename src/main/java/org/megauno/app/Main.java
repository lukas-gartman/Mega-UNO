package org.megauno.app;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.megauno.app.application.MegaUNO;

public class Main {
//    public static void main(String[] args) {
//        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
//        System.out.println("Type s for server and just enter for client");
//        String answer = new Scanner(System.in).nextLine();
//        ApplicationAdapter app = new ClientScreen();
//        if (answer.equals("s")) {
//            app = new ModelApplication();
//        }
//        new Lwjgl3Application(app, config);
//    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(1600, 900);
        new Lwjgl3Application(new MegaUNO(), config);
    }

}
