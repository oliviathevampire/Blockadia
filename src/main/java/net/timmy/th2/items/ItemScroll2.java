package net.timmy.th2.items;

import net.timmy.th2.Thaumania2;
import net.timmy.th2.properties.EnumRuneType2;

public class ItemScroll2 extends ItemMod {

    public final EnumRuneType2 color;

    public ItemScroll2(EnumRuneType2 color) {
        super("scroll_" + color.getName());
        this.color = color;
        setCreativeTab(Thaumania2.th2Items);
    }

}
