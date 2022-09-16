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
        while (players.playersLeft() > 1) {

            ICard choice = players.currentMakeTurn(top);
            while(!choice.isPlayable(top)){
                players.returnCard(choice);
                choice = players.currentMakeTurn(top);
            }
            top = choice;
            players.nextTurn();
        }
    }
}
