package org.megauno.app.viewcontroller.lobby;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.lwjgl.opengl.GL20;
import org.megauno.app.utility.dataFetching.DataFetcher;
import org.megauno.app.utility.dataFetching.PathDataFetcher;
import org.megauno.app.viewcontroller.IDrawable;

import java.util.ArrayList;
import java.util.Arrays;

public class LobbyApplication extends ApplicationAdapter {
    private Stage stage;
    private Batch batch;
    private IDrawable drawable;

    static Sprite background;
    static Table table;

    private String nickname = "";

    public LobbyApplication() {

    }

    @Override
    public void create() {
        DataFetcher<String, Sprite> spriteDataFetcher = new PathDataFetcher<>(
                key -> new Sprite(new Texture(key)), "assets/"
        );
        this.background = spriteDataFetcher.tryGetDataSafe("lobby-bg.png");
        Skin skin = new Skin(Gdx.files.internal("assets/skins/mega-uno-skin.json"));

        stage = new Stage(new ScreenViewport());
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
                nicknameButton.setDisabled(true);
                joinButton.setDisabled(true);
                hostButton.setDisabled(true);

                clearLabels(feedbackLabels);
                joinFeedback.setColor(Color.GREEN);
                joinFeedback.setText("Waiting for host to start the game...");
//                lobbyList.getItems().add(nickname);
                // todo: Launch ClientApplication
            }
        });
        onChange(hostButton, () -> {
            String port = hostPortTextField.getText();

            if (nickname.equals(""))
                hostFeedback.setText("Please enter a nickname first");
            else if (port.equals(""))
                hostFeedback.setText("Port number cannot be empty");
            else if (!port.matches("^(0|6[0-5][0-5][0-3][0-5]|[1-5][0-9][0-9][0-9][0-9]|[1-9][0-9]{0,3})$"))
                hostFeedback.setText("Port number must be in the range 0-65535");
            else {
                nicknameButton.setDisabled(true);
                joinButton.setDisabled(true);
                hostButton.setDisabled(true);
                hostStartButton.setVisible(true);

                clearLabels(feedbackLabels);
                hostFeedback.setColor(Color.GREEN);
                hostFeedback.setText("Press start to start the game");
            }
        });
        onChange(hostStartButton, () -> {
            // todo: Launch ModelApplication
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

        nicknameTable.setPosition(400, 550);
        joinTable.setPosition(400, 400);
        hostTable.setPosition(400, 250);
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

    static public void onChange (Actor actor, Runnable listener) {
        actor.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                listener.run();
            }
        });
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        Gdx.gl.glClearColor(0, 0, 0, 0);
        batch.begin();
//        drawable.draw(Gdx.graphics.getDeltaTime(), batch);
        batch.draw(LobbyApplication.background, 0, 0, 1600, 900);
        batch.end();

        stage.draw();
        stage.act(Gdx.graphics.getDeltaTime());
    }
}
