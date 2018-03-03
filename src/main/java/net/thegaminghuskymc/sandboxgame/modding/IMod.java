package net.thegaminghuskymc.sandboxgame.modding;

import net.thegaminghuskymc.sgf.fml.common.event.FMLAddResourcesEvent;
import net.thegaminghuskymc.sgf.fml.common.event.FMLInitializationEvent;
import net.thegaminghuskymc.sgf.fml.common.event.FMLPostInitializationEvent;
import net.thegaminghuskymc.sgf.fml.common.event.FMLPreInitializationEvent;

public interface IMod {

    void preInit(FMLPreInitializationEvent event);

    void init(FMLInitializationEvent event);

    void postInit(FMLPostInitializationEvent event);

    void addResources(FMLAddResourcesEvent event);

}
