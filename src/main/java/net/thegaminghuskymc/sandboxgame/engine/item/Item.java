package net.thegaminghuskymc.sandboxgame.engine.item;

public abstract class Item {

    /** unique item id, set when the item is registered */
    private final short id;

    public Item(short id) {
        this.id = id;
    }

    /** item id */
    public final int getID() {
        return (this.id);
    }

}