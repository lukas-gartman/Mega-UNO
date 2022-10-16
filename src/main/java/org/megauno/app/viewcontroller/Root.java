package org.megauno.app.viewcontroller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.megauno.app.network.IdCard;
import org.megauno.app.network.PlayersCards;
import org.megauno.app.utility.Publisher.condition.ConPublisher;
import org.megauno.app.utility.Publisher.normal.Publisher;
import org.megauno.app.viewcontroller.players.GameView;

public class Root implements ViewPublisher{
    private Publisher<Integer> onNewPlayer = new Publisher<>();
    private Publisher<IdCard> onNewTopCard = new Publisher<>();
    private ConPublisher<PlayersCards>onCardsAddedToPlayer = new ConPublisher<>();
    private ConPublisher<PlayersCards> onCardsRemovedByPlayer = new ConPublisher<>();

    private GameView gameView;
    private Batch batch;
    public Root(int playerID, int[] otherPlayersIds, GameController gameController) {
        batch = new SpriteBatch();
        gameView = new GameView(playerID,otherPlayersIds,this, gameController);
    }


    public void draw(){
        gameView.draw(Gdx.graphics.getDeltaTime(), batch);
    }

    @Override
    public Publisher<Integer> onNewPlayer() {
        return onNewPlayer;
    }

    @Override
    public Publisher<IdCard> onNewTopCard() {
        return onNewTopCard;
    }

    @Override
    public ConPublisher<PlayersCards> onCardsAddedToPlayer() {
        return onCardsAddedToPlayer;
    }

    @Override
    public ConPublisher<PlayersCards> onCardsRemovedByPlayer() {
        return onCardsRemovedByPlayer;
    }

}
