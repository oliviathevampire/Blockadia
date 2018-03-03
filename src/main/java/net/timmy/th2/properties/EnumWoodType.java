package net.timmy.th2.properties;

import net.thegaminghuskymc.sandboxgame.util.IStringSerializable;

public enum EnumWoodType implements IStringSerializable {

    WEIRWOOD("Weirwood", 0),
    CHARGED_WEIRWOOD("Charged_Weirwood", 1),
    THORDWYRD("Thornwyrd", 2),
    GRAND_OAK("Grand_Oak", 3),
    ETERNAL("Eternal", 4);

    private static final EnumWoodType[] METADATA_LOOKUP = new EnumWoodType[values().length];

    static {
        for (EnumWoodType type : values()) {
            METADATA_LOOKUP[type.getMeta()] = type;
        }
    }

    private String name;
    private int ID;

    EnumWoodType(String name, int ID) {
        this.name = name;
        this.ID = ID;
    }

    public static EnumWoodType byMetadata(int metadata) {

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
