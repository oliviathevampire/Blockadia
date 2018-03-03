package net.timmy.th2;

import net.thegaminghuskymc.sandboxgame.client.Minecraft;
import net.thegaminghuskymc.sandboxgame.creativetab.CreativeTabs;
import net.thegaminghuskymc.sandboxgame.init.Items;
import net.thegaminghuskymc.sandboxgame.item.ItemStack;
import net.thegaminghuskymc.sandboxgame.util.NonNullList;

public class Th2Tab extends CreativeTabs {

    private String title = "";
    private boolean hoveringButton = false;

    private ItemStack icon = ItemStack.EMPTY;
    private boolean displayRandom = true;
    private int tempIndex = 0;
    private ItemStack tempDisplayStack = ItemStack.EMPTY;

    Th2Tab(String label) {
        super(label);
//        setBackgroundImageName(LibResources.GUI_CREATIVE);
    }

    @Override
    public ItemStack getIconItemStack() {
        if (this.displayRandom) {
            if (Minecraft.getSystemTime() % 120 == 0) {
                this.updateDisplayStack();
            }
            return this.tempDisplayStack;
        } else return this.icon;
    }

    @Override
    public String getTranslatedTabLabel() {
        return hoveringButton ? title : getTabLabel();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setHoveringButton(boolean hoveringButton) {
        this.hoveringButton = hoveringButton;
    }

    private void updateDisplayStack() {
        if (this.displayRandom) {
            NonNullList<ItemStack> itemStacks = NonNullList.create();
//            this.displayAllRelevantItems(itemStacks);
            this.tempDisplayStack = !itemStacks.isEmpty() ? itemStacks.get(tempIndex) : ItemStack.EMPTY;
            if (++tempIndex >= itemStacks.size()) tempIndex = 0;
        } else {
            if (this.icon.isEmpty()) {
                this.tempDisplayStack = new ItemStack(Items.DIAMOND);
            }
            this.tempDisplayStack = this.icon;
        }
    }

    @Override
    public ItemStack getTabIconItem() {
        return this.getIconItemStack();
    }

}