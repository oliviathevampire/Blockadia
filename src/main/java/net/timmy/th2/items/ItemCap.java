package net.timmy.th2.items;

import net.timmy.th2.Thaumania2;
import net.timmy.th2.properties.EnumWandCapType;

public class ItemCap extends ItemMod {

    public final EnumWandCapType color;

    public ItemCap(EnumWandCapType color) {
        super("wand_cap_" + color.getName());
        this.color = color;
        setCreativeTab(Thaumania2.th2Wands);
    }

}
