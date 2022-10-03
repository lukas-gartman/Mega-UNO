package org.megauno.app.network;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    GridPane gridPane;
    private TextField port;
    private TextField joinHostname;
    private TextField joinPort;
    Button hostButton;
    Button joinButton;
    boolean searchingForPlayers = true;

    private void host() {
        gridPane.getChildren().clear();
        Button startGame = new Button("Start Game");
        startGame.setOnAction(event -> searchingForPlayers = false);

        ListView<String> connectedClients = new ListView<>();

        gridPane.add(connectedClients, 0,0);
        gridPane.add(startGame, 0,1);

        int p = Integer.parseInt(port.getText());
        Server server = new Server(p);
        new Thread(server).start();
        Client testClient = new Client("localhost", 1337);
        new Thread(() -> {
            HashMap<ClientHandler,Integer> clientHandlers;
            List<String> strings = new ArrayList<>();

            while (searchingForPlayers) {
                clientHandlers = server.getClientHandlers();
                if (clientHandlers.size() != strings.size()) {
                    strings.clear();
                    for (int x : clientHandlers.values())
                        strings.add("Client #" + x);
                    Platform.runLater(() -> {
                        connectedClients.getItems().clear();
                        connectedClients.getItems().addAll(strings);
                    });
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        Client test2 = new Client("localhost", 1337);
    }

    private void join() {
        gridPane.getChildren().clear();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Lobby");

        port = new TextField();
        port.setPromptText("port");
        port.setText("1337");
        hostButton = new Button("Host");
        hostButton.setOnAction(event -> host());

        joinHostname = new TextField();
        joinHostname.setPromptText("hostname");
        joinHostname.setText("localhost");
        joinPort = new TextField();
        joinPort.setPromptText("port");
        joinPort.setText("1337");
        joinButton = new Button("Join");
        joinButton.setOnAction(event -> join());


        gridPane = new GridPane();
        gridPane.setMinSize(400, 200);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        gridPane.add(port, 0, 0);
        gridPane.add(hostButton, 1, 0);
        gridPane.add(joinHostname, 0, 1);
        gridPane.add(joinPort, 1,1);
        gridPane.add(joinButton, 2, 1);
        primaryStage.setScene(new Scene(gridPane));
        primaryStage.show();
    }
}

//public class Main {
//    public static void main(String[] args) {
//        new Thread(new Server(1337)).start();
//        Client client = new Client("localhost", 1337);
//        Client c2 = new Client("localhost", 1337);
//
//        JSONObject json = new JSONObject();
//        json.put("message", "suh dude from json!");
//        c2.sendJSON(json);
//    }
//}
