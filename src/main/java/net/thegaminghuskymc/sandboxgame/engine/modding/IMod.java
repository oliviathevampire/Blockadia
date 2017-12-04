package net.thegaminghuskymc.sandboxgame.engine.modding;

public interface IMod {

    /*void preInit(Mod mod);

    void init(Mod mod);

    void postInit(Mod mod);*/

    /**
     * called when the mod should be initialized
     */
    public void initialize(Mod mod);

    /**
     * called when the mod should be deinitialized
     */
    public void deinitialize(Mod mod);

}
