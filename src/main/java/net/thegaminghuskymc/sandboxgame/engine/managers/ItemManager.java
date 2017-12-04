package net.thegaminghuskymc.sandboxgame.engine.managers;

import net.thegaminghuskymc.sandboxgame.engine.Logger;
import net.thegaminghuskymc.sandboxgame.engine.item.Item;

public class ItemManager extends GenericManager<Item> {

    protected ItemManager(ResourceManager resource_manager) {
        super(resource_manager);
    }

    /**
     * register an item to the engine
     */
    public Item registerItem(Item item) {
        Logger.get().log(Logger.Level.FINE, "Registering an item: " + item.toString());
        super.registerObject(item);
        return (item);
    }

    @Override
    protected void onObjectRegistered(Item item) {
    }

    @Override
    public void onInitialized() {
    }

    @Override
    public void onLoaded() {
    }

    @Override
    protected void onDeinitialized() {
    }

    @Override
    protected void onUnloaded() {
    }

    /**
     * get an item by it id
     */
    public Item getItemByID(short itemID) {
        return (super.getObjectByID(itemID));
    }
}
