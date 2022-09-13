package Game;

// Circular doubly linked list to keep track of players and turns
// dependency on Node class
public class PlayerCircle {
    private Node head = null;
    private Node tail = null;
    private Node currentPlayer = null;
    rotation direction;

    public PlayerCircle(){
        // starts with clockwise as default
        direction = rotation.CLOCKWISE;
    }

    private void addNode(Player p) {
        Node newNode = new Node(p);
        if (head == null) {
            head = newNode;

            // Sets first added player as current player:
            currentPlayer = newNode;

        } else {
            tail.nextNode = newNode;
            newNode.previousNode = tail;
        }
        tail = newNode;

        tail.nextNode = head;
        head.previousNode = tail;
    }

    // Changes currentPlayer to next in line depending on current rotation
    public void nextPlayer(){
        if (direction == rotation.CLOCKWISE) currentPlayer = currentPlayer.nextNode;
        else currentPlayer = currentPlayer.previousNode;
    }

    public void currentMakeTurn(){
        currentPlayer.play();
    }

    public void changeRotation(){
        if (direction == rotation.CLOCKWISE) direction = rotation.ANTICLOCKWISE;
        else direction = rotation.CLOCKWISE;
    }
}

enum rotation {
    CLOCKWISE,
    ANTICLOCKWISE
}
