package Game;

public class Game {
    PlayerCircle players;
    ICard top;
    // deck
    // discardPile

    public Game(PlayerCircle enemies){
        this.players = enemies;
    }

    public void reverse(){
        players.changeRotation();
    }

    public void play() {
        players.nextTurn();

    }
}
