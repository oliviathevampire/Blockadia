package net.thegaminghuskymc.sandboxgame.engine.item;


import net.thegaminghuskymc.sandboxgame.engine.registries.IForgeRegistryEntry;
import net.thegaminghuskymc.sandboxgame.engine.util.ResourceLocation;
import net.thegaminghuskymc.sandboxgame.engine.world.entity.WorldEntityLiving;

import javax.annotation.Nullable;

public class Item implements IForgeRegistryEntry<Item> {

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
    public void onUnequipped(WorldEntityLiving entity, int equipmentID) {
    }

    /**
     * Sets a unique name for this Item. This should be used for uniquely identify the instance of the Item.
     * This is the valid replacement for the atrocious 'getUnlocalizedName().substring(6)' stuff that everyone does.
     * Unlocalized names have NOTHING to do with unique identifiers. As demonstrated by vanilla blocks and items.
     * <p>
     * The supplied name will be prefixed with the currently active mod's modId.
     * If the supplied name already has a prefix that is different, it will be used and a warning will be logged.
     * <p>
     * If a name already exists, or this Item is already registered in a registry, then an IllegalStateException is thrown.
     * <p>
     * Returns 'this' to allow for chaining.
     *
     * @param name Unique registry name
     * @return This instance
     */
    @Override
    public Item setRegistryName(ResourceLocation name) {
        return null;
    }

    /**
     * A unique identifier for this entry, if this entry is registered already it will return it's official registry name.
     * Otherwise it will return the name set in setRegistryName().
     * If neither are valid null is returned.
     *
     * @return Unique identifier or null.
     */
    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return null;
    }

    @Override
    public Class<Item> getRegistryType() {
        return null;
    }

    /**
     * called when this item is equipped on a living entity at the given
     * equipment id
     */
    public void onEquipped(WorldEntityLiving entity, int equipmentID) {
    }

}