package net.timmy.th2.properties;

import net.thegaminghuskymc.sandboxgame.util.IStringSerializable;

import java.awt.*;

public enum EnumRuneType2 implements IStringSerializable {

    TIWAZ("tiwaz", "the_god_tiwaz", "T", 0, new Color(255, 255, 255), 1, "victory", "honor"),
    BERKANAN("berkanan", "birch", "B", 1, new Color(255, 255, 255), 1, "fertillity", "growth", "sustenance"),
    EHWAZ("ehwaz", "horse", "E", 2, new Color(255, 255, 255), 1, "trust", "faith", "companionship"),
    MANNAZ("mannaz", "man", "M", 3, new Color(255, 255, 255), 1, "augmentation", "support"),
    LAGUZ("laguz", "laguz", "L", 4, new Color(255, 255, 255), 1, "formlessness", "chaos", "potentiality", "the unknown"),
    INGWAZ("ingwaz", "the_god_ingwaz", "Ng", 5, new Color(255, 255, 255), 1, "fertillization", "the_beginning_of_something", "the_actualization_of_potential"),
    OTHALAN("othalan", "inheritance", "O", 6, new Color(255, 255, 255), 1, "inheritance", "heritage", "tradition", "nobility"),
    DAGAZ("dagaz", "day", "D", 7, new Color(255, 255, 255), 1, "hope", "happiness");

    private static final EnumRuneType2[] METADATA_LOOKUP = new EnumRuneType2[values().length];

    static {
        for (EnumRuneType2 type : values()) {
            METADATA_LOOKUP[type.getMeta()] = type;
        }
    }

    private String rune_name;
    private String name;
    private String phoneme;
    private int ID;
    private Color color;
    private int ept;
    private String meaning;
    private String meaning2;
    private String meaning3;
    private String meaning4;

    EnumRuneType2(String rune_name, String name, String phoneme, int ID, Color color, int ept, String meaning, String meaning2) {
        this.rune_name = rune_name;
        this.name = rune_name;
        this.phoneme = phoneme;
        this.ID = ID;
        this.color = color;
        this.ept = ept;
        this.meaning = meaning;
        this.meaning2 = meaning2;
    }

    EnumRuneType2(String rune_name, String name, String phoneme, int ID, Color color, int ept, String meaning, String meaning2, String meaning3) {
        this.rune_name = rune_name;
        this.name = rune_name;
        this.phoneme = phoneme;
        this.ID = ID;
        this.color = color;
        this.ept = ept;
        this.meaning = meaning;
        this.meaning2 = meaning2;
        this.meaning3 = meaning3;
    }

    EnumRuneType2(String rune_name, String name, String phoneme, int ID, Color color, int ept, String meaning, String meaning2, String meaning3, String meaning4) {
        this.rune_name = rune_name;
        this.name = rune_name;
        this.phoneme = phoneme;
        this.ID = ID;
        this.color = color;
        this.ept = ept;
        this.meaning = meaning;
        this.meaning2 = meaning2;
        this.meaning3 = meaning3;
        this.meaning4 = meaning4;
    }

    public static EnumRuneType2 byMetadata(int metadata) {

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
        return this.rune_name;
    }

    public String getPhoneme() {
        return phoneme;
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

    public String getMeaning4() {
        return meaning4;
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

}