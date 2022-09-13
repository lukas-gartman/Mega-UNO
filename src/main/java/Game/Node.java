package Game;

// Nodes are players and their neighbours
public class Node {
    private Player player;
    public Node nextNode;
    public Node previousNode;

    public Node(Player p) {
        this.player = p;
    }

    public void play(){
        player.play();
    }
}
