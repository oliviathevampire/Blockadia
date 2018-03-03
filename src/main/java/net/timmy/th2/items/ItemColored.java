package net.timmy.th2.items;

import net.minecraft.item.EnumDyeColor;

public class ItemColored extends ItemMod {

    public final EnumDyeColor color;

    public ItemColored(String name, EnumDyeColor color) {
        super(name + "_" + color.getName());
        this.color = color;
    }

}
