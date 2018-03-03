package net.timmy.th2.items;

import net.timmy.th2.Thaumania2;
import net.timmy.th2.properties.EnumWandCombinationsDefault;

public class ItemWand extends ItemMod {

    public final EnumWandCombinationsDefault color;

    public ItemWand(EnumWandCombinationsDefault color) {
        super("wand_" + color.getName());
        this.color = color;
        setCreativeTab(Thaumania2.th2Wands);
    }

}
