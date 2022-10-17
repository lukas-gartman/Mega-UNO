package org.megauno.app.model.Cards.Impl;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.megauno.app.model.Cards.Color;
import org.megauno.app.model.Cards.ICard;

public class CardValidationTest {

    ICard nc1 = new NumberCard(Color.RED, 5);
    //ICard nc2 = new NumberCard(Color., 5);

    @Test(expected = IllegalArgumentException.class)
    public void testValidateColor() {
        CardValidation.validateColor(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateNumber() {
        CardValidation.validateNumber(13);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateType() {
        CardValidation.validateType(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateColorNc() {
        CardValidation.validateColorNc(Color.NONE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testColorIndex() {
        Color.getFromIndex(28);
    }
}