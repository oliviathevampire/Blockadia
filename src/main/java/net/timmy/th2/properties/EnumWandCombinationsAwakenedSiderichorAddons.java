package net.timmy.th2.properties;

import net.thegaminghuskymc.sandboxgame.util.IStringSerializable;

public enum EnumWandCombinationsAwakenedSiderichorAddons implements IStringSerializable {

    AWAKENED_SIDERICHOR_AND_THORNWYRD("awakened_siderichor_thornwyrd", 0),
    AWAKENED_SIDERICHOR_AND_GRAND_OAK("awakened_siderichor_grand_oak", 1),
    AWAKENED_SIDERICHOR_AND_BIG_OAK("awakened_siderichor_big_oak", 2),
    AWAKENED_SIDERICHOR_AND_ACACIA("awakened_siderichor_acacia", 3),
    AWAKENED_SIDERICHOR_AND_JUNGLE("awakened_siderichor_jungle", 4),
    AWAKENED_SIDERICHOR_AND_BIRCH("awakened_siderichor_birch", 5),
    AWAKENED_SIDERICHOR_AND_SPRUCE("awakened_siderichor_spruce", 6);

    private static final EnumWandCombinationsAwakenedSiderichorAddons[] METADATA_LOOKUP = new EnumWandCombinationsAwakenedSiderichorAddons[values().length];

    static {
        for (EnumWandCombinationsAwakenedSiderichorAddons type : values()) {
            METADATA_LOOKUP[type.getID()] = type;
        }
    }

    protected String name;
    protected int ID;

    EnumWandCombinationsAwakenedSiderichorAddons(String name, int ID) {
        this.name = name;
        this.ID = ID;
    }

    public static EnumWandCombinationsAwakenedSiderichorAddons byMetadata(int metadata) {

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