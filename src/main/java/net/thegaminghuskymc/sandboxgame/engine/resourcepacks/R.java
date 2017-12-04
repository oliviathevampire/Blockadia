package net.thegaminghuskymc.sandboxgame.engine.resourcepacks;

import net.thegaminghuskymc.sandboxgame.engine.managers.LangManager;
import net.thegaminghuskymc.sandboxgame.engine.managers.ResourceManager;

/**
 * a simple wrapper class which make ResourceManager expression shorten
 */
public class R {

    public static String getResPath(String modID, String filepath) {
        return (ResourceManager.instance().getResourcePath(modID, filepath));
    }

    /**
     * get the resource path for a standart resource
     */
    public static String getResPath(String filepath) {
        return (ResourceManager.instance().getResourcePath("huskys_sandbox_game", filepath));
    }

    /**
     * return the string value of the given word
     */
    public static String getWord(String word) {
        String str = getString("word." + word);
        if (str == null) {
            return (word);
        }
        return (str);
    }

    /**
     * return the string value for the given string ID
     */
    public static String getString(String strid) {
        return (LangManager.instance().getString(strid));
    }
}
