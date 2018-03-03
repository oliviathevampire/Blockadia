package net.timmy.th2.properties;

import net.thegaminghuskymc.sandboxgame.util.IStringSerializable;

public enum EnumWandCombinationsCoalAddons implements IStringSerializable {

    COAL_AND_OAK("coal_oak", 0),
    COAL_AND_WEIRWOOD("coal_weirwood", 1),
    COAL_AND_CHARGED_WEIRWOOD("coal_charged_weirwood", 2),
    COAL_AND_THORNWYRD("coal_thornwyrd", 3),
    COAL_AND_GRAND_OAK("coal_grand_oak", 4),
    COAL_AND_BIG_OAK("coal_big_oak", 5),
    COAL_AND_ACACIA("coal_acacia", 6),
    COAL_AND_JUNGLE("coal_jungle", 7),
    COAL_AND_BIRCH("coal_birch", 8),
    COAL_AND_SPRUCE("coal_spruce", 9);

    private static final EnumWandCombinationsCoalAddons[] METADATA_LOOKUP = new EnumWandCombinationsCoalAddons[values().length];

    static {
        for (EnumWandCombinationsCoalAddons type : values()) {
            METADATA_LOOKUP[type.getID()] = type;
        }
    }

    protected String name;
    protected int ID;

    EnumWandCombinationsCoalAddons(String name, int ID) {
        this.name = name;
        this.ID = ID;
    }

    public static EnumWandCombinationsCoalAddons byMetadata(int metadata) {

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