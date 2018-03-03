package net.timmy.th2.items;

import net.timmy.th2.Thaumania2;
import net.timmy.th2.properties.EnumRuneType;
import net.timmy.th2.properties.EnumRuneType2;

public class ItemRune2 extends ItemMod {

    public final EnumRuneType2 color;

    public ItemRune2(EnumRuneType2 color) {
        super("rune_" + color.getName());
        this.color = color;
        setCreativeTab(Thaumania2.th2Items);
    }

}
