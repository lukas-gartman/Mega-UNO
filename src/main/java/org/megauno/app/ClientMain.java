package org.megauno.app;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.json.JSONArray;
import org.json.JSONObject;
import org.megauno.app.network.Client;
import org.megauno.app.network.IdCard;
import org.megauno.app.network.PlayersCards;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientMain {
    public static void main( String[] args ) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nickname: ");
        //String nickname = scanner.nextLine();
        System.out.print("Host name: ");
        //String hostName = scanner.nextLine();
        System.out.print("Port (0-65535): ");
        //int port = scanner.nextInt();

        ClientApplication app = new ClientApplication();

        Client c = new Client("dude", "localhost", 1337, o -> {
            app.respondToJSON(o);
            if(o.getString("Type").equals("Start")){
                Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
                //config.setMaximized(true);
                new Lwjgl3Application(app, config);
            }
        });




    }

}
