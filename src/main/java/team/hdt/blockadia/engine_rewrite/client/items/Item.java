package team.hdt.blockadia.engine_rewrite.client.items;

import java.util.List;

public class Item {

    public boolean isInHand;
    public List enchantments;


    public Item() {
    }
    /*TODO: item needs to be made
    * took the json util from mine-craft will need to create the return can
    * me phoenix for to give the item class used in mine-craft if needed
    */
    public static Item getByNameOrId(String s) {
        return null;
    }


    public List getEnchantments() {
        return enchantments;
    }

    public void setEnchantments(List enchantments) {
        this.enchantments = enchantments;
    }
}
