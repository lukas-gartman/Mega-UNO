package Cards;

import java.util.Objects;

public class ActionCard implements Card {
   // Card reverseCard = new ActionCard(REVERSE, new RerseAction(), blue)
  // Card reverseCard = new ReverseCard(..., blue) --
   private final IAction action;
   private final Color color;

   public ActionCard(IAction action, Color color) {
      this.action = action;
      this.color = color;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof ActionCard)) return false;
      ActionCard that = (ActionCard) o;
      return action.equals(that.action) && color == that.color;
   }

   @Override
   public int hashCode() {
      return Objects.hash(action, color);
   }

   //public void getAction();
}
