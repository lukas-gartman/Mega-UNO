package org.megauno.app.model.Cards.Impl;

import org.megauno.app.model.Cards.*;
import org.megauno.app.model.Game.IActOnGame;

import javax.lang.model.type.TypeKind;
import java.util.Objects;

/**
 * Specifies an action/special card in UNO. An action card has an action which it can execute,
 * has a color and a type, which specifies what specific action card it is upon creation.
 */
public class ActionCard extends AbstractCard {
   private final IAction action;

   /**
    * The only constructor of an action card.
    * The constructor validates the card, i.e. checks that the given arguments
    * are valid for an action card.
    * @param action The action which the card can execute.
    * @param color The color of the card.
    * @param type The specific type of action card.
    */
   public ActionCard(IAction action, Color color, CardType type) {
      super(color, type);
      CardValidation.validateColor(color);
      CardValidation.validateType(type);
      //validate action
      // check that type and action correlates
      this.action = action;
   }

   public IAction getAction() {
      return this.action;
   }

   @Override
   public String toString() {
      return "ActionCard{" +
              "action=" + action +
              ", color=" + super.getColor() +
              ", type=" + super.getType() +
              '}';
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof ActionCard that)) return false;
      return action.getClass() == ((ActionCard) o).getAction().getClass() && this.getColor() == that.getColor();
   }

   @Override
   public int hashCode() {
      return Objects.hash(action, this.getColor());
   }

   @Override
   public boolean activate(IActOnGame g) {
      return action.execute(g);
   }

   @Override
   public boolean canBePlayed(ICard c) {
      return c.canBePlayedOnMe(this);
   }

   @Override
   public boolean canBePlayedOnMe(NumberCard c) {
     return this.getColor() == c.getColor();
   }

   @Override
   public boolean canBePlayedOnMe(ActionCard c) {
     return c.getType() == CardType.WILDCARD || c.getColor() == this.getColor() || c.getType() == this.getType();
   }

   // Shallow copy, no need for deep copy since the attributes are immutable.
   @Override
   public ICard copyCard() {
      return new ActionCard(this.action, this.getColor(), this.getType());
   }

   @Override
   public boolean canBeStacked(ICard c) {
      return c.canBeStackedUnder(this);
   }

   @Override
   public boolean canBeStackedUnder(ActionCard ac) {
      return ac.getAction().getClass() == action.getClass();
   }

   @Override
   public boolean canBeStackedUnder(NumberCard nc) {
      return false;
   }
}
