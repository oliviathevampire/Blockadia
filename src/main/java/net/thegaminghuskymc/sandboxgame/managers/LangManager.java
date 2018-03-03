package net.thegaminghuskymc.sandboxgame.managers;

import net.thegaminghuskymc.sandboxgame.GameEngine;
import net.thegaminghuskymc.sandboxgame.resourcepacks.R;

import java.util.HashMap;

public class LangManager extends GenericManager<HashMap<String, String>> {

    public static int EN_US;
    public static int EN_UK;
    public static int NO_NB;
    public static int SW_SW;
    private static LangManager _instance;
    private HashMap<String, String> lang;

    public LangManager(ResourceManager resource_manager) {
        super(resource_manager);
        _instance = this;
    }

    private static String capitalizeStr(String str) {
        if (str.length() == 0) {
            return (str);
        }

        if (str.length() == 0) {
            return ("" + Character.toUpperCase(str.charAt(0)));
        }
        return (Character.toUpperCase(str.charAt(0)) + str.toLowerCase().substring(1, str.length()));
    }

    public static LangManager instance() {
        return (_instance);
    }

    @Override
    public void onInitialized() {
    }

    @Override
    public void onLoaded() {
        EN_US = this.registerLang(GameEngine.instance().getModId(), "en_US");
        EN_UK = this.registerLang(GameEngine.instance().getModId(), "en_UK");
        NO_NB = this.registerLang(GameEngine.instance().getModId(), "no_NB");
        SW_SW = this.registerLang(GameEngine.instance().getModId(), "sw_SW");
        this.setLang(EN_US);
    }

    @Override
    protected void onDeinitialized() {
        this.lang = null;
    }

    @Override
    protected void onUnloaded() {
        this.lang = null;
    }

    /**
     * register a lang to the default voxel engine assets dir
     */
    private int registerLang(String langID) {
        return (super.registerObject(ResourceManager.getConfigFile(R.getResPath(GameEngine.instance().getModId(), "lang/" + langID + ".lang"), 1024)));
    }

    /**
     * register a new lang
     *
     * @return the lang id
     */
    private int registerLang(String modid, String langID) {
        return (super.registerObject(
                ResourceManager.getConfigFile(super.getResource(modid, "lang/" + langID + ".lang"), 1024)));
    }

    /**
     * set the language to be use
     */
    public void setLang(int langID) {
        this.lang = this.getObjectByID(langID);
    }

    /**
     * get the string with the given id
     */
    public String getString(String strid) {
        if (this.lang == null || strid == null || strid.length() == 0) {
            return (null);
        }

        return (this.lang.get(strid));
    }

    @Override
    protected void onObjectRegistered(HashMap<String, String> object) {
    }
}
