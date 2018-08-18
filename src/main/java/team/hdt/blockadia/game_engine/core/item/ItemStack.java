package team.hdt.blockadia.game_engine.core.item;

import team.hdt.blockadia.game_engine.core.util.IItemProvider;

public class ItemStack {

    public static final ItemStack EMPTY = new ItemStack(null);
    private int stackSize;
    @Deprecated
    private final ItemType item;

    public ItemStack(IItemProvider itemProvider) {
        this(itemProvider, 1);
    }

    public ItemStack(IItemProvider itemProvider, int stackSize) {
        this.item = itemProvider == null ? null : itemProvider.getItem();
        this.stackSize = stackSize;
    }

    public ItemType getItem() {
        return item;
    }

}