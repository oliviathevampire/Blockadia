package net.timmy.timmylib.utils;

import com.google.common.collect.Lists;
import net.minecraft.util.text.translation.I18n;

import java.util.List;

@SuppressWarnings("deprecation")
public class Lang {
    public static final char CHAR = '|';
    private static String prefix;

    public Lang(String locKey) {
        prefix = locKey.concat(".");
    }

    public static String getPrefix() {
        return prefix;
    }

    public static String addPrefix(String suffix) {
        return prefix.concat(suffix);
    }

    public static String localize(String unloc, Object... args) {
        return localizeExact(addPrefix(unloc), args);
    }

    public static String localize(String unloc) {
        return localizeExact(addPrefix(unloc));
    }

    public static String localizeExact(String unloc, Object... args) {
        return I18n.translateToLocalFormatted(unloc, args);
    }

    public static String localizeExact(String unloc) {
        return I18n.translateToLocal(unloc);
    }

    public static String[] localizeList(String unloc, String... args) {
        return splitList(localize(unloc, (Object[]) args));
    }

    public static String[] localizeList(String unloc) {
        return splitList(localize(unloc));
    }

    public static List<String> localizeAll(List<String> unloc) {
        List<String> ret = Lists.newArrayList();
        for (String s : unloc) {
            ret.add(localize(s));
        }
        return ret;
    }

    @SuppressWarnings("static-access")
    public static String[] localizeAll(Lang lang, String... unloc) {
        String[] ret = new String[unloc.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = lang.localize(unloc[i]);
        }
        return ret;
    }

    public static String[] splitList(String list) {
        return list.split("\\|");
    }

    public static String[] splitList(String list, String split) {
        return list.split(split);
    }

    public static boolean canLocalize(String unloc) {
        return canLocalizeExact(addPrefix(unloc));
    }

    public static boolean canLocalizeExact(String unloc) {
        return I18n.canTranslate(unloc);
    }
}