package org.megauno.app.application;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.lwjgl.opengl.GL20;
import org.megauno.app.utility.dataFetching.DataFetcher;
import org.megauno.app.utility.dataFetching.PathDataFetcher;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LobbyScreen extends ScreenAdapter {
    private MegaUNO megaUNO;
    private Viewport viewport;
    private Stage stage;
    private Batch batch;
    private Sprite background;
    private String nickname = "";
    private List<TextButton> buttons;
    private ServerHost serverHost;

    public LobbyScreen(MegaUNO megaUNO) {
        this.megaUNO = megaUNO;
        this.viewport = new ExtendViewport(megaUNO.WINDOW_WIDTH, megaUNO.WINDOW_HEIGHT);
    }

    @Override
    public void show() {
        DataFetcher<String, Sprite> spriteDataFetcher = new PathDataFetcher<>(
                key -> new Sprite(new Texture(key)), "assets/"
        );
        this.background = spriteDataFetcher.tryGetDataSafe("lobby-bg.png");
        Skin skin = new Skin(Gdx.files.internal("assets/skins/mega-uno-skin.json"));

        stage = new Stage();
        stage.setViewport(viewport);
        Gdx.input.setInputProcessor(stage);
//        drawable = new LobbyView();
        batch = new SpriteBatch();


        // CONNECTED CLIENTS
//        Table lobbyTable = new Table();
        Table nicknameTable = new Table();
        Table joinTable = new Table();
        Table hostTable = new Table();

//        Label lobbyLabel = new Label("Lobby", skin);
        Label nicknameLabel = new Label("Nickname", skin);
        Label joinHostnameLabel = new Label("Hostname", skin);
        Label joinPortLabel = new Label("Port", skin);
        Label hostPortLabel = new Label("Port", skin);

        Label nicknameFeedback = new Label("", skin);
        Label joinFeedback = new Label("", skin);
        Label hostFeedback = new Label("", skin);
        java.util.List<Label> feedbackLabels = Arrays.asList(nicknameFeedback, joinFeedback, hostFeedback);

        joinFeedback.setColor(Color.RED);
        hostFeedback.setColor(Color.RED);

//        List<String> lobbyList = new List<>(skin);

        // NICKNAME
        TextField nicknameTextField = new TextField("", skin);
        TextField hostnameTextField = new TextField("", skin);
        TextField joinPortTextField = new TextField("", skin);
        TextField hostPortTextField = new TextField("", skin);

        TextButton nicknameButton = new TextButton("SET", skin);
        TextButton joinButton = new TextButton("JOIN", skin);
        TextButton hostButton = new TextButton("HOST", skin);
        TextButton hostStartButton = new TextButton("START", skin);
        buttons = new ArrayList<>();
        buttons.add(nicknameButton);
        buttons.add(joinButton);
        buttons.add(hostButton);
        buttons.add(hostStartButton);


        hostStartButton.setVisible(false);

        onChange(nicknameButton, () -> {
            nickname = nicknameTextField.getText();
            if (nickname.equals("")) {
                nicknameFeedback.setColor(Color.RED);
                nicknameFeedback.setText("Nickname cannot be empty");
            } else {
                nicknameFeedback.setColor(Color.GREEN);
                nicknameFeedback.setText("Nickname has been set");
            }
        });
        onChange(joinButton, () -> {
            String hostname = hostnameTextField.getText();
            String port = joinPortTextField.getText();

            if (nickname.equals(""))
                joinFeedback.setText("Please enter a nickname first");
            else if (hostname.equals(""))
                joinFeedback.setText("Hostname cannot be empty");
            else if (port.equals(""))
                joinFeedback.setText("Port number cannot be empty");
            else if (!port.matches("^(0|6[0-5][0-5][0-3][0-5]|[1-5][0-9][0-9][0-9][0-9]|[1-9][0-9]{0,3})$"))
                joinFeedback.setText("Port number must be in the range 0-65535");
            else {
                try {
                    ClientScreen clientScreen = new ClientScreen(megaUNO, nickname, hostname, Integer.parseInt(port));

                    nicknameButton.setDisabled(true);
                    joinButton.setDisabled(true);
                    hostButton.setDisabled(true);

                    clearLabels(feedbackLabels);
                    joinFeedback.setColor(Color.GREEN);
                    joinFeedback.setText("Waiting for host to start the game...");
//                lobbyList.getItems().add(nickname);
                    Gdx.graphics.setWindowedMode(650, 500);

                    megaUNO.setScreen(clientScreen);
                } catch (ConnectException ex) {
                    joinFeedback.setText(ex.getMessage());
                }
            }
        });
        onChange(hostButton, () -> {
            String port = hostPortTextField.getText();
            Integer i = null;
            try {
                i = Integer.parseInt(port);
            } catch (Exception e) {
            }

            if (nickname.equals(""))
                hostFeedback.setText("Please enter a nickname first");
            else if (port.equals(""))
                hostFeedback.setText("Port number cannot be empty");
            else if (i == null)
                hostFeedback.setText("Port number must be a number");
            else if (!port.matches("^(0|6[0-5][0-5][0-3][0-5]|[1-5][0-9][0-9][0-9][0-9]|[1-9][0-9]{0,3})$"))
                hostFeedback.setText("Port number must be in the range 0-65535");
            else {
                try {
                    this.serverHost = new ServerHost();
                    serverHost.createLobby(Integer.parseInt(port));

                    nicknameButton.setDisabled(true);
                    joinButton.setDisabled(true);
                    hostButton.setDisabled(true);
                    hostStartButton.setVisible(true);

                    clearLabels(feedbackLabels);
                    hostFeedback.setColor(Color.GREEN);
                    hostFeedback.setText("Press start to start the game");
                } catch (IllegalAccessException e) {
                    hostFeedback.setColor(Color.RED);
                    hostFeedback.setText("Port is already in use");
                }
            }
        });
        onChange(hostStartButton, () ->
        {
            int port = Integer.parseInt(hostPortTextField.getText());
            try {
                ClientScreen clientScreen = new ClientScreen(megaUNO, nickname, "localhost", port);
                Gdx.graphics.setWindowedMode(650, 500);
                megaUNO.setScreen(clientScreen);
                this.serverHost.start();
            } catch (ConnectException ex) {
                hostFeedback.setText(ex.getMessage());
            }
        });


        nicknameTable.add(nicknameLabel);
        nicknameTable.row();
        nicknameTable.add(nicknameTextField).size(200, 35);
        nicknameTable.add(nicknameButton).size(100, 35).pad(10);
        nicknameTable.add(nicknameFeedback).width(100).left();

        joinTable.add(joinHostnameLabel);
        joinTable.row();
        joinTable.add(hostnameTextField).size(200, 35).pad(10, 0, 10, 0);
        joinTable.row();
        joinTable.add(joinPortLabel);
        joinTable.row();
        joinTable.add(joinPortTextField).size(200, 35);
        joinTable.add(joinButton).size(100, 35).pad(10);
        joinTable.add(joinFeedback).width(100).left();

        hostTable.add(hostPortLabel);
        hostTable.row();
        hostTable.add(hostPortTextField).align(Align.center).size(200, 35);
        hostTable.add(hostButton).size(100, 35).pad(10);
        hostTable.add(hostFeedback).width(100).left();
        hostTable.row();
        hostTable.add(hostStartButton).size(100, 35).pad(10);

//        lobbyTable.add(lobbyLabel);
//        lobbyTable.row().pad(10);
//        lobbyTable.add(lobbyList);

        nicknameTable.setPosition(-300, 150);
        joinTable.setPosition(-300, 0);
        hostTable.setPosition(-300, -150); //250
//        lobbyTable.setPosition(800, 550);

        stage.addActor(nicknameTable);
        stage.addActor(joinTable);
        stage.addActor(hostTable);
//        stage.addActor(lobbyTable);
    }

    private void clearLabels(java.util.List<Label> labels) {
        for (Label label : labels)
            label.setText("");
    }

    static public void onChange(Actor actor, Runnable listener) {
        actor.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                listener.run();
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        megaUNO.batch.begin();
        megaUNO.batch.draw(background, 0, 0, megaUNO.WINDOW_WIDTH, megaUNO.WINDOW_HEIGHT);
        megaUNO.batch.end();

        stage.draw();
        stage.act(Gdx.graphics.getDeltaTime());

        super.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        viewport.setWorldSize(width, height);
        stage.getViewport().update(width, height);
    }

    @Override
    public void hide() {
        for (Actor actor : stage.getActors())
            actor.remove();
    }

    @Override
    public void dispose() {
        if (serverHost != null)
            serverHost.close();
        stage.dispose();
        super.dispose();
    }

}
