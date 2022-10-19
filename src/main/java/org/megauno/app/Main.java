package org.megauno.app;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import java.util.Scanner;

public class Main {
    public static void main( String[] args ) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
//      config.setMaximized(true);
        new Lwjgl3Application(new ModelApplication(), config);
    }

}
