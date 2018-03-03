package net.timmy.timmylib.utils;

import net.minecraft.client.resources.I18n;

public class Translations {

    public static final String TOOLTIP_PRESS = translate("tooltip", "press");
    public static final String TOOLTIP_INFO = translate("tooltip", "info");
    public static final String TOOLTIP_SHIFT = translate("tooltip", "shift");

    public static final String TOOLTIP_CRAFT = translate("tooltip", "shift");
    public static final String TOOLTIP_CRAFT_WITH = translate("tooltip", "craft", "with");
    public static final String TOOLTIP_SMELT = translate("tooltip", "smelt");

    private static String translate(String prefix, String key) {
        return I18n.format(prefix + "." + Refs.MODID + "." + key + ".name");
    }

    private static String translate(String prefix, String key, String suffix) {
        return I18n.format(prefix + "." + Refs.MODID + "." + key + "." + suffix);
    }

}