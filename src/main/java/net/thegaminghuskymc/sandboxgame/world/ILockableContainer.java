package net.thegaminghuskymc.sandboxgame.world;

import net.thegaminghuskymc.sandboxgame.inventory.IInventory;

public interface ILockableContainer extends IInventory, IInteractionObject
{
    boolean isLocked();

    void setLockCode(LockCode code);

    LockCode getLockCode();
}