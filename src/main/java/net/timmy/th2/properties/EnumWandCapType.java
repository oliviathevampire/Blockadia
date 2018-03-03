package net.timmy.th2.properties;

import net.thegaminghuskymc.sandboxgame.util.IStringSerializable;

public enum EnumWandCapType implements IStringSerializable {

    IRON("iron", 0),
    GOLD("gold", 1),
    SIDERICHOR("siderichor", 2),
    AWAKENED_SIDERICHOR("awakened_siderichor", 3),
    COAL("coal", 4),
    DIAMOND("diamond", 5);

    private static final EnumWandCapType[] METADATA_LOOKUP = new EnumWandCapType[values().length];

    static {
        for (EnumWandCapType type : values()) {
            METADATA_LOOKUP[type.getID()] = type;
        }
    }

    private String name;
    private int ID;

    EnumWandCapType(String name, int ID) {
        this.name = name;
        this.ID = ID;
    }

    public static EnumWandCapType byMetadata(int metadata) {

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
        return this.name;
    }

    public int getID() {
        return this.ID;
    }

}
