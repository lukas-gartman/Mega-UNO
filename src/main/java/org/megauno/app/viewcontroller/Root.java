package org.megauno.app.viewcontroller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.lwjgl.opengl.GL20;
import org.megauno.app.network.IdCard;
import org.megauno.app.network.PlayersCards;
import org.megauno.app.utility.Publisher.condition.ConPublisher;
import org.megauno.app.utility.Publisher.normal.Publisher;
import org.megauno.app.viewcontroller.players.GameView;

import java.util.Arrays;

public class Root {


    private GameView gameView;
    private  ViewController vc;
    private Batch batch;
    public Root(int playerID, int[] otherPlayersIds, GameController gameController, ViewPublisher viewPublisher) {

        //vc = new ViewController(playerID,otherPlayersIds,viewPublisher,gameController);
        gameView = new GameView(playerID, otherPlayersIds, viewPublisher, gameController);
        batch = new SpriteBatch();
    }

    public void draw(){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Draw a game view
        batch.begin();
        gameView.draw(Gdx.graphics.getDeltaTime(), batch);
        batch.end();
    }


}
