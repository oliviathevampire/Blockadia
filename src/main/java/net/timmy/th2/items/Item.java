package net.timmy.th2.items;

import net.thegaminghuskymc.huskylib2.lib.items.ItemMod;
import net.timmy.th2.Reference;

public class Item extends ItemMod {

    public Item(String name) {
        super(name);
    }

    @Override
    public String getModNamespace() {
        return Reference.MODID;
    }

}
