package net.timmy.th2.properties;

import net.thegaminghuskymc.sandboxgame.util.IStringSerializable;

public enum EnumWandCombinationsGoldAddons implements IStringSerializable {

    GOLD_AND_THORNWYRD("iron_thornwyrd", 0),
    GOLD_AND_GRAND_OAK("iron_grand_oak", 1),
    GOLD_AND_BIG_OAK("iron_big_oak", 2),
    GOLD_AND_ACACIA("iron_acacia", 3),
    GOLD_AND_JUNGLE("iron_jungle", 4),
    GOLD_AND_BIRCH("iron_birch", 5),
    GOLD_AND_SPRUCE("iron_spruce", 6);

    private static final EnumWandCombinationsGoldAddons[] METADATA_LOOKUP = new EnumWandCombinationsGoldAddons[values().length];

    static {
        for (EnumWandCombinationsGoldAddons type : values()) {
            METADATA_LOOKUP[type.getID()] = type;
        }
    }

    protected String name;
    protected int ID;

    EnumWandCombinationsGoldAddons(String name, int ID) {
        this.name = name;
        this.ID = ID;
    }

    public static EnumWandCombinationsGoldAddons byMetadata(int metadata) {

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