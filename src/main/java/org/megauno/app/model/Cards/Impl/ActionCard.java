package org.megauno.app.model.Cards.Impl;

import org.megauno.app.model.Cards.*;

import javax.lang.model.type.TypeKind;
import java.util.Objects;

public class ActionCard extends AbstractCard {
   private final IAction action;

   public ActionCard(IAction action, Color color, CardType type) {
      super(color, type);
      CardValidation.validateColor(color);
      CardValidation.validateType(type);
      //validate action
      // check that type and action correlates
      this.action = action;
   }

   //public void getAction();
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
   public boolean activate() {
      return action.execute();
   }

   @Override
   public boolean canBePlayed(ICard c) {
      return c.canBePlayedOnMe(this);
   }

      //return c.visit(this);
   @Override
   public boolean canBePlayedOnMe(NumberCard c) {
     return this.getColor() == c.getColor();
   }

   //This can probably be abstracted in the abstract class
   @Override
   public boolean canBePlayedOnMe(ActionCard c) {
     return c.getColor() == Color.NONE || c.getColor() == this.getColor() || c.getType() == this.getType();
   }

   // Shallow copy, no need for deep copy since the attributes are immutable.
   @Override
   public ICard copyCard() {
      return new ActionCard(this.action, this.getColor(), this.getType());
   }

   // The visit methods checks that the given card can be placed on themselves
   @Override
   public boolean visit(ActionCard ac) {
      return ac.getType() == CardType.WILDCARD || ac.getColor() == this.getColor() || ac.getType() == this.getType();
   }

   @Override
   public boolean visit(NumberCard nc) {
      return this.getColor() == nc.getColor();
   }

   @Override
   public boolean canBeStacked(ICard c) {
      return c.canBeStackedUnder(this);
   }

   @Override
   public boolean canBeStackedUnder(ActionCard ac) {
      return ac.action == this.action;
   }

   @Override
   public boolean canBeStackedUnder(NumberCard nc) {
      return false;
   }
}
