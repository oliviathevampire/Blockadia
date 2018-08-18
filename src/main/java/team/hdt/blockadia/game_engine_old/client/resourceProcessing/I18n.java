//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package team.hdt.blockadia.game_engine_old.client.resourceProcessing;

public class I18n {
    private static Locale i18nLocale;

    static void setLocale(Locale p_setLocale_0_) {
        i18nLocale = p_setLocale_0_;
    }

    public static String format(String p_format_0_, Object... p_format_1_) {
        return i18nLocale.formatMessage(p_format_0_, p_format_1_);
    }

    public static boolean hasKey(String p_hasKey_0_) {
        return i18nLocale.hasKey(p_hasKey_0_);
    }
}
