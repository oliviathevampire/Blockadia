package net.timmy.th2.items;

import net.minecraft.item.EnumDyeColor;
import net.timmy.th2.Thaumania2;

public class ItemChalk extends ItemColored {

    public ItemChalk(EnumDyeColor color) {
        super("chalk", color);
        setCreativeTab(Thaumania2.th2Items);
    }

}
