package net.timmy.th2.properties;

import net.thegaminghuskymc.sandboxgame.util.IStringSerializable;

public enum EnumWandCoreType implements IStringSerializable {

    OAK("oak", 0),
    GOLD("gold", 1),
    WEIRWOOD("weirwood", 2),
    CHARGED_WEIRWOOD("charged_weirwood", 3),
    THORNWYRD("thornwyrd", 4),
    GRAND_OAK("grand_oak", 5),
    BIG_OAK("big_oak", 6),
    ACACIA("acacia", 7),
    JUNGLE("jungle", 8),
    BIRCH("birch", 9),
    SPRUCE("spruce", 10),
    DIAMOND("diamond", 11),
    COAL("coal", 12);

    private static final EnumWandCoreType[] METADATA_LOOKUP = new EnumWandCoreType[values().length];

    static {
        for (EnumWandCoreType type : values()) {
            METADATA_LOOKUP[type.getID()] = type;
        }
    }

    private String name;
    private int ID;

    EnumWandCoreType(String name, int ID) {
        this.name = name;
        this.ID = ID;
    }

    public static EnumWandCoreType byMetadata(int metadata) {

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
