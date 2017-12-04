package net.thegaminghuskymc.sandboxgame.engine.item;

import net.thegaminghuskymc.sandboxgame.engine.world.entity.EntityLiving;

public abstract class Item {

    /**
     * unique item id, set when the item is registered
     */
    private final short id;

    public Item(short id) {
        this.id = id;
    }

    /**
     * item id
     */
    public final int getID() {
        return (this.id);
    }

    /**
     * called when this item is unequipped on a living entity at the given
     * equipment id
     */
    public void onUnequipped(EntityLiving entity, int equipmentID) {
    }

    /**
     * called when this item is equipped on a living entity at the given
     * equipment id
     */
    public void onEquipped(EntityLiving entity, int equipmentID) {
    }

}