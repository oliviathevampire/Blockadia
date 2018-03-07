package net.thegaminghuskymc.sandboxgame.engine.world.entity;

import net.thegaminghuskymc.sandboxgame.engine.item.Item;
import net.thegaminghuskymc.sandboxgame.engine.world.World;

public abstract class WorldEntityLiving extends WorldEntity {

    /**
     * default inventory size
     */
    private static final int DEFAULT_EQUIPMENT_COUNT = 1;

    /**
     * the items held by this entity
     */
    private final Item[] equipments;

    /**
     * the entity rigid body
     */

    public WorldEntityLiving(World world) {
        this(world, 1.0f, 1.0f, 1.0f, 1.0f);
    }

    public WorldEntityLiving(World world, float mass, float width, float height, float depth) {
        super(world, mass, width, height, depth);
        if (this.getEquipmentCount() > 0) {
            this.equipments = new Item[this.getEquipmentCount()];
        } else {
            this.equipments = null;
        }
    }

    /**
     * equip an item to the given biped part
     */
    public void equip(Item item, int equipmentID) {
        if (equipmentID < 0 || equipmentID >= this.getEquipmentCount()) {
            return;
        }

        if (this.equipments[equipmentID] != null) {
            this.equipments[equipmentID].onUnequipped(this, equipmentID);
        }

        this.equipments[equipmentID] = item;

        if (this.equipments[equipmentID] != null) {
            this.equipments[equipmentID].onEquipped(this, equipmentID);
        }
    }

    public void unequip(int equipmentID) {
        this.equip(null, equipmentID);
    }

    /**
     * return the number of equipment which can be equipped
     */
    public int getEquipmentCount() {
        return (WorldEntityLiving.DEFAULT_EQUIPMENT_COUNT);
    }

    public Item[] getEquipments() {
        return (this.equipments == null ? null : this.equipments.clone());
    }

}
