package Game;

public class Game {
    PlayerCircle enemies;

    public Game(PlayerCircle enemies){
        this.enemies = enemies;
    }

    public void reverseCard(){
        enemies.changeRotation();
    }

    public void nextPlayer(){
        enemies.nextPlayer();
    }
}
