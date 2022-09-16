package Game;

// Nodes are players and their neighbours
public class Node {
    private Player player;
    public Node nextNode;
    public Node previousNode;

    public Node(Player p) {
        this.player = p;
    }

    public ICard play(ICard top){
        return player.play(top);
    }

    void returnCard(ICard choice){
        player.addCard(choice);
    }
}
