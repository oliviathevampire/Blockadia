package net.timmy.th2.init;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.timmy.th2.Reference;
import net.timmy.th2.Thaumania2;
import net.timmy.th2.entity.EntityMagicMaster;
import net.timmy.th2.entity.EntityWitch;
import net.timmy.th2.entity.renders.RenderMagicMaster;
import net.timmy.th2.entity.renders.RenderWitch;

public class Th2Entities {

    public static Entity magic_master, witch;

    static {
        magic_master = new EntityMagicMaster(Minecraft.getMinecraft().world);
        witch = new EntityWitch(Minecraft.getMinecraft().world);
    }

    public static void register() {
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, "magic_master"), EntityMagicMaster.class, "magic_master", 0, Thaumania2.instance, 10, 4, true);
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, "witch"), EntityWitch.class, "witch", 1, Thaumania2.instance, 10, 4, true);
    }

    public static void registerRenders() {
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        RenderingRegistry.registerEntityRenderingHandler(EntityMagicMaster.class, new RenderMagicMaster(renderManager));
        RenderingRegistry.registerEntityRenderingHandler(EntityWitch.class, new RenderWitch(renderManager));
    }

}
