package net.timmy.th2.entity.renders;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.timmy.th2.Reference;
import net.timmy.th2.entity.EntityWitch;
import net.timmy.th2.entity.models.ModelWitch;
import org.lwjgl.opengl.GL11;

public class RenderWitch extends RenderLiving<EntityWitch> {

    public static final ResourceLocation witchTexture = new ResourceLocation(Reference.MODID, "textures/entity/witch.png");

    public RenderWitch(RenderManager renderManager) {
        super(renderManager, new ModelWitch(0.0F), 1F);
    }

    @Override
    public void doRender(EntityWitch _entity, double posX, double posY, double posZ, float var8, float var9) {
        EntityWitch entity = (EntityWitch) _entity;

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_CULL_FACE);
        super.doRender(entity, posX, posY, posZ, var8, var9);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glPopMatrix();
    }

    @Override
    protected void preRenderCallback(EntityWitch entityliving, float f) {
//		GL11.glRotatef(180F, 0, 1F, 0F);
//		GL11.glRotatef(180F, 0, 0, 1F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityWitch var1) {
        return witchTexture;
    }
}