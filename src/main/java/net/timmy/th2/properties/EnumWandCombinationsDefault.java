package net.timmy.th2.properties;

import net.thegaminghuskymc.sandboxgame.util.IStringSerializable;

public enum EnumWandCombinationsDefault implements IStringSerializable {

    IRON_AND_OAK("iron_oak", 0),
    IRON_AND_GOLD("iron_gold", 1),
    IRON_AND_WEIRWOOD("iron_weirwood", 2),
    IRON_AND_CHARGED_WEIRWOOD("iron_charged_weirwood", 3),
    GOLD_AND_OAK("gold_oak", 4),
    GOLD_AND_GOLD("gold", 5),
    GOLD_AND_WEIRWOOD("gold_weirwood", 6),
    GOLD_AND_CHARGED_WEIRWOOD("gold_charged_weirwood", 7),
    SIDERICHOR_AND_OAK("siderichor_oak", 8),
    SIDERICHOR_AND_GOLD("siderichor_gold", 9),
    SIDERICHOR_AND_WEIRWOOD("siderichor_weirwood", 10),
    SIDERICHOR_AND_CHARGED_WEIRWOOD("siderichor_charged_weirwood", 11),
    AWAKENED_SIDERICHOR_AND_OAK("awakened_siderichor_oak", 12),
    AWAKENED_SIDERICHOR_AND_GOLD("awakened_siderichor_gold", 13),
    AWAKENED_SIDERICHOR_AND_WEIRWOOD("awakened_siderichor_weirwood", 14),
    AWAKENED_SIDERICHOR_AND_CHARGED_WEIRWOOD("awakened_siderichor_charged_weirwood", 15);

    private static final EnumWandCombinationsDefault[] METADATA_LOOKUP = new EnumWandCombinationsDefault[values().length];

    static {
        for (EnumWandCombinationsDefault type : values()) {
            METADATA_LOOKUP[type.getID()] = type;
        }
    }

    private String name;
    private int ID;

    EnumWandCombinationsDefault(String name, int ID) {
        this.name = name;
        this.ID = ID;
    }

    public static EnumWandCombinationsDefault byMetadata(int metadata) {

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
        return ID;
    }

}