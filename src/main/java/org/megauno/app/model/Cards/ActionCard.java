package org.megauno.app.model.Cards;

import java.util.Objects;

public class ActionCard extends AbstractCard {
   // Card reverseCard = new ActionCard(REVERSE, new RerseAction(), blue)
  // Card reverseCard = new ReverseCard(..., blue) --
   private final IAction action;

   public ActionCard(IAction action, Color color, CardType type) {
      super(color, type);
      CardValidation.validateColor(color);
      CardValidation.validateType(type);
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
   public boolean canBePlayed(ICard c) {
      return c.canBePlayedOnMe(this);
/*
      if (this.getColor() == Color.NONE)
         return true;
      else {
         return this.getColor() == c.getColor() || this.getType() == c.getType();
      }
*/

   }

   @Override
   public boolean canBePlayedOnMe(NumberCard c) {
     return this.getColor() == c.getColor();
   }

   //This can probably be abstracted in the abstract class
   @Override
   public boolean canBePlayedOnMe(ActionCard c) {
     return c.getColor() == Color.NONE || c.getColor() == this.getColor() || c.getType() == this.getType();
   }
   //public void getAction();

   // Shallow copy, no need for deep copy since the attributes are immutable.
   @Override
   public ICard copyCard() {
      return new ActionCard(this.action, this.getColor(), this.getType());
   }
}
