package net.timmy.timmylib;

import net.thegaminghuskymc.sandboxgame.modding.ModInfo;
import net.thegaminghuskymc.sgf.fml.common.event.FMLAddResourcesEvent;
import net.thegaminghuskymc.sgf.fml.common.event.FMLInitializationEvent;
import net.thegaminghuskymc.sgf.fml.common.event.FMLPostInitializationEvent;
import net.thegaminghuskymc.sgf.fml.common.event.FMLPreInitializationEvent;
import net.timmy.timmylib.utils.Refs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ModInfo(modid = Refs.MODID, name = Refs.NAME, version = Refs.VERSION, acceptedMinecraftVersions = Refs.ACC_MC)
public class HuskyLib {

    public static final Logger logger = LogManager.getFormatterLogger(Refs.MODID);
    @ModInfo.Instance(value = Refs.MODID)
    public static HuskyLib instance = new HuskyLib();

    @ModInfo.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

    }

    @ModInfo.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @ModInfo.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

    @ModInfo.EventHandler
    public void addResources(FMLAddResourcesEvent event) {

    }


}
