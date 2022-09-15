package Cards;

import java.util.Objects;

public class ActionCard extends AbstractCard {
   // Card reverseCard = new ActionCard(REVERSE, new RerseAction(), blue)
  // Card reverseCard = new ReverseCard(..., blue) --
   private final IAction action;

   public ActionCard(IAction action, Color color, CardType type) {
      super(color, type);
      CardValidation.validateColor(color);
      //validate action
      // check that type and action correlates
      this.action = action;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof ActionCard that)) return false;
      return action.equals(that.action) && this.getColor() == that.getColor();
   }

   @Override
   public int hashCode() {
      return Objects.hash(action, this.getColor());
   }

   @Override
   public boolean canBePlayed(Card c) {
      if (this.getColor() == Color.NONE)
         return true;
      else {
         return this.getColor() == c.getColor() || this.getType() == c.getType();
      }
   }
   //public void getAction();
}
