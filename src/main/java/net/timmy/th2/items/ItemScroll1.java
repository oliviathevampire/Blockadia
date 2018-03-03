package net.timmy.th2.items;

import net.timmy.th2.Thaumania2;
import net.timmy.th2.properties.EnumRuneType;

public class ItemScroll1 extends ItemMod {

    public final EnumRuneType color;

    public ItemScroll1(EnumRuneType color) {
        super("scroll_" + color.getName());
        this.color = color;
        setCreativeTab(Thaumania2.th2Items);
    }

}
