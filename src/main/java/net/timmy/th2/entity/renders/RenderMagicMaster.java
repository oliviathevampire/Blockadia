package net.timmy.th2.entity.renders;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.timmy.th2.Reference;
import net.timmy.th2.entity.EntityMagicMaster;
import net.timmy.th2.entity.layers.LayerHeldItemWitch;
import net.timmy.th2.entity.models.ModelMagicMaster;

@SideOnly(Side.CLIENT)
public class RenderMagicMaster extends RenderLiving<EntityMagicMaster> {
    private static final ResourceLocation WITCH_TEXTURES = new ResourceLocation(Reference.MODID, "textures/entity/magic_master.png");

    public RenderMagicMaster(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelMagicMaster(0.0F), 0.5F);
        this.addLayer(new LayerHeldItemWitch(this));
    }

    public ModelMagicMaster getMainModel() {
        return (ModelMagicMaster) super.getMainModel();
    }

    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(EntityMagicMaster entity, double x, double y, double z, float entityYaw, float partialTicks) {
        ((ModelMagicMaster) this.mainModel).holdingItem = !entity.getHeldItemMainhand().isEmpty();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityMagicMaster entity) {
        return WITCH_TEXTURES;
    }

    public void transformHeldFull3DItemLayer() {
        GlStateManager.translate(0.0F, 0.1875F, 0.0F);
    }

    /**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    protected void preRenderCallback(EntityMagicMaster entitylivingbaseIn, float partialTickTime) {
        float f = 0.9375F;
        GlStateManager.scale(0.9375F, 0.9375F, 0.9375F);
    }
}