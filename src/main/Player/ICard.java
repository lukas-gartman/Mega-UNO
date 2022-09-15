package Player;

public interface ICard {
    boolean canBePlayedOn(ICard topCard);

    ICard copyCard();
}
