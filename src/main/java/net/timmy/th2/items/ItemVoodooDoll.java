package net.timmy.th2.items;

import net.minecraft.item.EnumDyeColor;
import net.timmy.th2.Thaumania2;

public class ItemVoodooDoll extends ItemColored {

    public ItemVoodooDoll(EnumDyeColor color) {
        super("voodoo_doll", color);
        setCreativeTab(Thaumania2.th2Items);
    }

}
