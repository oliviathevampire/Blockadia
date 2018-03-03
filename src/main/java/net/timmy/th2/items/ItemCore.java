package net.timmy.th2.items;

import net.timmy.th2.Thaumania2;
import net.timmy.th2.properties.EnumWandCoreType;

public class ItemCore extends ItemMod {

    public final EnumWandCoreType color;

    public ItemCore(EnumWandCoreType color) {
        super("wand_core_" + color.getName());
        this.color = color;
        setCreativeTab(Thaumania2.th2Wands);
    }

}
