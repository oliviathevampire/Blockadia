package net.timmy.th2;

import net.thegaminghuskymc.sandboxgame.creativetab.CreativeTabs;
import net.thegaminghuskymc.sandboxgame.util.text.TextFormatting;
import net.thegaminghuskymc.sgf.fml.common.SidedProxy;
import net.thegaminghuskymc.sgf.fml.common.event.FMLAddResourcesEvent;
import net.thegaminghuskymc.sgf.fml.common.event.FMLInitializationEvent;
import net.thegaminghuskymc.sgf.fml.common.event.FMLPostInitializationEvent;
import net.thegaminghuskymc.sgf.fml.common.event.FMLPreInitializationEvent;
import net.thegaminghuskymc.sandboxgame.modding.IMod;
import net.thegaminghuskymc.sandboxgame.modding.ModInfo;
import net.timmy.th2.proxy.CommonProxy;

@ModInfo(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION,creator = Reference.CREATOR)
public class Thaumania2 implements IMod {

    @ModInfo.Instance
    public static Thaumania2 instance;

    public static CreativeTabs th2Blocks = new Th2Tab(TextFormatting.GOLD + "Thaumania 2: Blocks");
    public static CreativeTabs th2Items = new Th2Tab(TextFormatting.GOLD + "Thaumania 2: Items");
    public static CreativeTabs th2Wands = new Th2Tab(TextFormatting.GOLD + "Thaumania 2: Wands");

    @SidedProxy(clientSide = Reference.CSIDE, serverSide = Reference.SSIDE)
    public static CommonProxy proxy;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Override
    public void addResources(FMLAddResourcesEvent event) {
        proxy.addResources(event);
    }

}