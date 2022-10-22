package org.megauno.app.utility;

import java.util.Objects;

public class EqualsReferenceWrapper<Item> {
    private final Item item;

    public EqualsReferenceWrapper(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    @Override
    public boolean equals(Object that) {
        try {
            EqualsReferenceWrapper<Item> thatWrapper = (EqualsReferenceWrapper<Item>) that;
            Item thatItem = thatWrapper.getItem();
            if(thatItem == item){
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            return  false;
        }
    }





    @Override
    public int hashCode() {
        return Objects.hash(item);
    }
}
