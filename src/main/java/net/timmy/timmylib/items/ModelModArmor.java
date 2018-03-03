package net.timmy.timmylib.items;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class ModelModArmor extends ModelBiped {

    public abstract void setModelParts();

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        setModelParts();

        GlStateManager.pushMatrix();
        if (entity instanceof EntityArmorStand) { // Fixes rendering on armor stands
            f3 = 0;
            GlStateManager.translate(0F, 0.15F, 0F);
        }

        prepareForRender(entity, f5);
        super.render(entity, f, f1, f2, f3, f4, f5);
        GlStateManager.popMatrix();
    }

    public void prepareForRender(Entity entity, float pticks) {
        EntityLivingBase living = (EntityLivingBase) entity;
        isSneak = living != null && living.isSneaking();
        isChild = living != null && living.isChild();
        if (living instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) living;

            swingProgress = player.getSwingProgress(pticks);

            ArmPose modelbiped$armpose = ArmPose.EMPTY;
            ArmPose modelbiped$armpose1 = ArmPose.EMPTY;
            ItemStack itemstack = player.getHeldItemMainhand();
            ItemStack itemstack1 = player.getHeldItemOffhand();

            if (!itemstack.isEmpty()) {
                modelbiped$armpose = ArmPose.ITEM;

                if (player.getItemInUseCount() > 0) {
                    EnumAction enumaction = itemstack.getItemUseAction();

                    if (enumaction == EnumAction.BLOCK)
                        modelbiped$armpose = ArmPose.BLOCK;
                    else if (enumaction == EnumAction.BOW)
                        modelbiped$armpose = ArmPose.BOW_AND_ARROW;
                }
            }

            if (!itemstack1.isEmpty()) {
                modelbiped$armpose1 = ArmPose.ITEM;

                if (player.getItemInUseCount() > 0) {
                    EnumAction enumaction1 = itemstack1.getItemUseAction();

                    if (enumaction1 == EnumAction.BLOCK)
                        modelbiped$armpose1 = ArmPose.BLOCK;
                }
            }

            if (player.getPrimaryHand() == EnumHandSide.RIGHT) {
                rightArmPose = modelbiped$armpose;
                leftArmPose = modelbiped$armpose1;
            } else {
                rightArmPose = modelbiped$armpose1;
                leftArmPose = modelbiped$armpose;
            }
        }
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

}
