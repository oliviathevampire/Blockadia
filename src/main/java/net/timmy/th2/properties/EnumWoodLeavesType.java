package net.timmy.th2.properties;

import net.thegaminghuskymc.sandboxgame.util.IStringSerializable;

public enum EnumWoodLeavesType implements IStringSerializable {

    WEIRWOOD("Weirwood", 0),
    GRAND_OAK("Grand_Oak", 1),
    ETERNAL("Eternal", 2);

    private static final EnumWoodLeavesType[] METADATA_LOOKUP = new EnumWoodLeavesType[values().length];

    static {
        for (EnumWoodLeavesType type : values()) {
            METADATA_LOOKUP[type.getMeta()] = type;
        }
    }

    private String name;
    private int ID;

    EnumWoodLeavesType(String name, int ID) {
        this.name = name;
        this.ID = ID;
    }

    public static EnumWoodLeavesType byMetadata(int metadata) {

        if (metadata < 0 || metadata >= METADATA_LOOKUP.length) {
            metadata = 0;
        }
        return METADATA_LOOKUP[metadata];
    }

    public static String[] toStringArray() {
        String[] array = new String[values().length];

        for (int i = 0; i < array.length; i++) {
            array[i] = values()[i].name;
        }

        return array;
    }

    @Override
    public String getName() {
        return this.name.toLowerCase();
    }

    public int getMeta() {
        return this.ID;
    }

}
