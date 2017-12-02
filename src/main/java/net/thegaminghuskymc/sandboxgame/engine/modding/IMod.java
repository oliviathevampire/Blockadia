package net.thegaminghuskymc.sandboxgame.engine.modding;

public interface IMod {

    void preInit(Mod mod);

    void init(Mod mod);

    void postInit(Mod mod);

}
