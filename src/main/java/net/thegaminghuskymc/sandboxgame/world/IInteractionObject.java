package net.thegaminghuskymc.sandboxgame.world;

import net.thegaminghuskymc.sandboxgame.entity.player.EntityPlayer;
import net.thegaminghuskymc.sandboxgame.entity.player.InventoryPlayer;
import net.thegaminghuskymc.sandboxgame.inventory.Container;

public interface IInteractionObject extends IWorldNameable
{
    Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn);

    String getGuiID();
}