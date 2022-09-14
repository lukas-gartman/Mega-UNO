package Player;


public interface Ihand {
     Icard[] getCards();
     Icard[] copyCards(Icard[] cards);
     void addCards(Icard[] cards);
}
