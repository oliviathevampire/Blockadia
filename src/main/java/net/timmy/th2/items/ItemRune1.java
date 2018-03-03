package net.timmy.th2.items;

import net.timmy.th2.Thaumania2;
import net.timmy.th2.properties.EnumRuneType;

public class ItemRune1 extends ItemMod {

    public final EnumRuneType color;

    public ItemRune1(EnumRuneType color) {
        super("rune_" + color.getName());
        this.color = color;
        setCreativeTab(Thaumania2.th2Items);
    }

}
