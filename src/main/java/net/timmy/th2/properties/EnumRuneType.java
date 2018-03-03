package net.timmy.th2.properties;

import net.thegaminghuskymc.sandboxgame.util.IStringSerializable;

import java.awt.*;

public enum EnumRuneType implements IStringSerializable {

    FEHU("fehu", "cattle", "F", 0, new Color(255, 255, 255), 1, "wealth"),
    URUZ("uruz", "aurochs", "U", 1, new Color(255, 255, 255), 1, "strength_of_will"),
    THURISAZ("thurisaz", "giant", "Th", 2, new Color(255, 255, 255), 1, "danger", "suffering"),
    ANSUZ("ansuz", "an_aesit_god", "A", 3, new Color(255, 255, 255), 1, "properity", "vitality"),
    RAIDHO("raidho", "journey_on_horseback", "R", 4, new Color(255, 255, 255), 1, "movement", "work", "growth"),
    KAUNAN("kaunan", "ulcer", "K", 5, new Color(255, 255, 255), 1, "mortality", "pain"),
    GEBO("gebo", "gift", "G", 6, new Color(255, 255, 255), 1, "generosity"),
    WUNJO("wunjo", "joy", "W", 7, new Color(255, 255, 255), 1, "joy", "ecstasy"),
    HAGALAZ("hagalaz", "hail", "H", 8, new Color(255, 255, 255), 1, "destruction", "chaos"),
    NAUDHIZ("naudhiz", "need", "N", 9, new Color(255, 255, 255), 1, "need", "unfulfilled_desire"),
    ISAZ("isaz", "ice", "I", 10, new Color(255, 255, 255), 1, "unknown"),
    JERA("jera", "year", "J", "Y", 11, new Color(255, 255, 255), 1, "harvest", "reward"),
    EIHWAZ("eihwaz", "yew", "I", 12, new Color(255, 255, 255), 1, "strength", "stability"),
    UNKNOWN_P("unknown_p", "unknown_p", "P", 13, new Color(255, 255, 255), 1, "unknown"),
    UNKNOWN_Z("unknown_z", "unknown_z", "Z", 14, new Color(255, 255, 255), 1, "protection_from_enemies", "defense_of_that_which_one_loves"),
    SOWILO("sowilo", "sun", "S", 15, new Color(255, 255, 255), 1, "success", "solance");

    private static final EnumRuneType[] METADATA_LOOKUP = new EnumRuneType[values().length];

    static {
        for (EnumRuneType type : values()) {
            METADATA_LOOKUP[type.getMeta()] = type;
        }
    }

    public boolean isMoreThan1True = true;
    public boolean isMoreThan1False = false;
    private String rune_name;
    private String name;
    private String phoneme;
    private String phoneme_germanic;
    private String phoneme_english;
    private int ID;
    private Color color;
    private int ept;
    private String meaning;
    private String meaning2;
    private String meaning3;

    EnumRuneType(String rune_name, String name, String phoneme, int ID, Color color, int ept, String meaning) {
        this.rune_name = rune_name;
        this.name = rune_name;
        this.phoneme = phoneme;
        this.ID = ID;
        this.color = color;
        this.ept = ept;
        this.meaning = meaning;
        hasMoreThan1Meanings(isMoreThan1False);
    }

    EnumRuneType(String rune_name, String name, String phoneme, int ID, Color color, int ept, String meaning, String meaning2) {
        this.rune_name = rune_name;
        this.name = rune_name;
        this.phoneme = phoneme;
        this.ID = ID;
        this.color = color;
        this.ept = ept;
        this.meaning = meaning;
        this.meaning2 = meaning2;
        hasMoreThan1Meanings(isMoreThan1True);
    }

    EnumRuneType(String rune_name, String name, String phoneme_germanic, String phoneme_english, int ID, Color color, int ept, String meaning, String meaning2) {
        this.rune_name = rune_name;
        this.name = rune_name;
        this.phoneme_germanic = phoneme_germanic;
        this.phoneme_english = phoneme_english;
        this.ID = ID;
        this.color = color;
        this.ept = ept;
        this.meaning = meaning;
        this.meaning2 = meaning2;
        hasMoreThan1Meanings(isMoreThan1True);
    }

    EnumRuneType(String rune_name, String name, String phoneme, int ID, Color color, int ept, String meaning, String meaning2, String meaning3) {
        this.rune_name = rune_name;
        this.name = rune_name;
        this.phoneme = phoneme;
        this.ID = ID;
        this.color = color;
        this.ept = ept;
        this.meaning = meaning;
        this.meaning2 = meaning2;
        this.meaning3 = meaning3;
        hasMoreThan1Meanings(isMoreThan1True);
    }

    public static EnumRuneType byMetadata(int metadata) {
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

    public boolean hasMoreThan1Meanings(boolean isMoreThan1) {
        return isMoreThan1 == isMoreThan1True;
    }

    @Override
    public String getName() {
        return this.rune_name;
    }

    public String getMeaning() {
        return meaning;
    }

    public String getMeaning2() {
        return meaning2;
    }

    public String getMeaning3() {
        return meaning3;
    }

    public String getPhoneme() {
        return phoneme;
    }

    public int getColor() {
        return color.getRGB();
    }

    public int getEpt() {
        return ept;
    }

    public int getMeta() {
        return ID;
    }

    public String getPhonemeEnglish() {
        return phoneme_english;
    }

    public String getPhonemeGermanic() {
        return phoneme_germanic;
    }

}